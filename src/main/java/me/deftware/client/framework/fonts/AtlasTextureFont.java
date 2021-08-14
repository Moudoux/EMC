package me.deftware.client.framework.fonts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import me.deftware.client.framework.main.bootstrap.Bootstrap;
import me.deftware.client.framework.render.batching.RenderStack;
import me.deftware.client.framework.render.texture.GlTexture;
import me.deftware.client.framework.util.path.OSUtils;

import java.awt.*;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

/**
 * @author Deftware, Ananas
 */
public class AtlasTextureFont {

    @Getter
    public final Map<String, CharData> characterMap = new HashMap<>();

    protected int fontSize;
    public boolean scaled;

    @Setter
    @Getter
    public int shadow = 1;

    @Getter
    private GlTexture textureAtlas;

    @Getter
    public int textureWidth, textureHeight;

    @Setter
    protected Font baseFont, stdFont;

    @Getter
    protected boolean ligatures = false;

    private FontMetrics metrics;

    public AtlasTextureFont(Font font, int fontSize, boolean scaled) {
        this.baseFont = font;
        this.fontSize = fontSize;
        this.scaled = scaled;
        RenderStack.scaleChangeCallback.add(() -> {
            setFont(baseFont);
            initialize();
        });
        setFont(baseFont);
    }

    public AtlasTextureFont(Font font, int fontSize) {
        this(font, fontSize, true);
    }

    public static Font getSystem(String name) {
        Font fallback = new Font(name, Font.PLAIN, 18);
        if (OSUtils.isWindows()) {
            try {
                File font = Paths.get(System.getenv("LOCALAPPDATA"), "Microsoft", "Windows", "Fonts", name + ".ttf").toFile();
                if (font.exists()) {
                    fallback = Font.createFont(Font.TRUETYPE_FONT, new FileInputStream(font.getAbsolutePath()));
                }
            } catch (Throwable ex) {
                ex.printStackTrace();
            }
        }
        return fallback;
    }

    private Font derive(Font font) {
        return font.deriveFont(Font.PLAIN, fontSize * (scaled ? RenderStack.getScale() : 1f));
    }

    public void setFont(Font font) {
        this.stdFont = derive(font);
        this.ligatures = this.stdFont.getAttributes().containsKey(TextAttribute.LIGATURES);
        setMetrics(font);
    }

    public void setMetrics(Font font) {
        this.metrics = new Canvas().getFontMetrics(derive(font));
    }

    public void initialize() {
        destroy();
        List<String> characters = new ArrayList<>();
        // https://en.wikipedia.org/wiki/List_of_Unicode_characters
        for (char numeric = 33; numeric <= 255; numeric++) {
            if (numeric == 160) // Non breaking space
                continue;
            characters.add(Character.toString(numeric));
        }
        // Ligatures
        if (this.ligatures) {
            characters.addAll(Arrays.asList(
                    "--", "---", "==", "===", "!=", "!==", "=!=", "=:=", "=/=", "<=", ">=", "&&", "&&&", "&=", "++", "+++", "***",
                    ";;", "!!", "??", "?:", "?.", "?=", "<:", ":<", ":>", ">:", "<>", "<<<", ">>>", "<<", ">>", "||", "-|", "_|_",
                    "|-", "||-", "|=", "||=", "##", "###", "####", "#{", "#[", "]#", "#(", "#?", "#_", "#_(", "#:", "#!", "#=", "^=",
                    "<$>", "<$", "$>", "<+>", "<+", "+>", "<*>", "<*", "*>", "</", "</>", "/>", "<!--", "<#--", "-->", "->", "->>", "<<-",
                    "<-", "<=<", "=<<", "<<=", "<==", "<=>", "<==>", "==>", "=>", "=>>", ">=>", ">>=", ">>-", ">-", ">--", "-<", "-<<", ">->",
                    "<-<", "<-|", "<=|", "|=>", "|->", "<->", "<~~", "<~", "<~>", "~~", "~~>", "~>", "~-", "-~", "~@", "[||]", "|]", "[|", "|}",
                    "{|", "[<", ">]", "|>", "<|", "||>", "<||", "|||>", "<|||", "<|>", "...", "..", ".=", ".-", "..<", ".?", "::", ":::", ":=",
                    "::=", ":?", ":?>", "//", "///", "/*", "*/", "/=", "//=", "/==", "@_", "__"
            ));
        }
        textureAtlas = characterGenerate(characters);
    }

    protected GlTexture characterGenerate(List<String> characters) {
        int characterWidth = 60, maxPerRow = 30;
        // Calculate size of texture
        // fixedWidth must be more than the width of the widest character
        int xLocation = 0, height = getStringHeight();
        List<Consumer<Graphics2D>> generation = new ArrayList<>();
        for (int i = 0; i < characters.size(); i++) {
            if (i > 0 && i % maxPerRow == 0) {
                height += getStringHeight() + 10;
                xLocation = 0;
            }
            String character = characters.get(i);
            int textWidth = getStringWidth(character), textHeight = getStringHeight();
            int xOffset = xLocation, yOffset = height - getStringHeight();
            generation.add(graphics -> {
                // Draw character
                graphics.drawString(character, xOffset, yOffset + (textHeight - textHeight / 4));
                // Generate data
                CharData data = new CharData(textWidth, textHeight, xOffset, yOffset, character);
                characterMap.put(character, data);
            });
            xLocation += characterWidth;
        }

        // Setup texture
        BufferedImage characterTexture = new BufferedImage(characterWidth * maxPerRow, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics = characterTexture.createGraphics();
        graphics.setFont(stdFont);
        graphics.setColor(Color.white);
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);

        // Draw characters
        generation.forEach(c -> c.accept(graphics));

        // Dispose graphics
        graphics.dispose();

        this.textureWidth = characterTexture.getWidth();
        this.textureHeight = characterTexture.getHeight();

        DataBuffer dataBuffer = characterTexture.getData().getDataBuffer();

        // Each bank element in the data buffer is a 32-bit integer
        long sizeBytes = ((long) dataBuffer.getSize()) * 4L;
        long sizeMB = sizeBytes / (1024L * 1024L);

        Bootstrap.logger.debug("Font atlas {}x{}, {} megabytes", textureWidth, textureHeight, sizeMB);

        return new GlTexture(characterTexture);
    }

    public int getStringWidth(char... chars) {
        return metrics.charsWidth(chars, 0, chars.length);
    }

    public int getStringWidth(String text) {
        return getStringWidth(text.toCharArray());
    }

    public int getStringHeight() {
        return metrics.getHeight();
    }

    public void destroy() {
        if (textureAtlas != null) {
            textureAtlas.destroy();
            characterMap.clear();
            textureAtlas = null;
        }
    }

    @Data
    @AllArgsConstructor
    public static class CharData {

        /**
         * The size of the character
         */
        private int width, height;

        /**
         * Texture offsets
         */
        private int u, v;

        private String character;

    }

}
