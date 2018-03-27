package me.deftware.mixin.annotations;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.ExecutableType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeVisitor;
import javax.lang.model.util.SimpleTypeVisitor8;
import javax.tools.StandardLocation;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

import static javax.lang.model.SourceVersion.RELEASE_8;
import static javax.lang.model.element.ElementKind.CONSTRUCTOR;
import static javax.lang.model.element.Modifier.PUBLIC;

@SupportedAnnotationTypes("me.deftware.mixin.annotations.ModInfo")
@SupportedSourceVersion(RELEASE_8)
public class ModInfoProcessor extends AbstractProcessor {
    private final TypeVisitor<Void, Void> checkInternalClassForStatic = new SimpleTypeVisitor8<Void, Void>() {
        @Override
        public Void visitDeclared(final DeclaredType t,
                                  final Void aVoid) {
            if (t.asElement().getEnclosingElement().getKind().isClass()) {
                t.asElement().getEnclosingElement().asType().accept(
                        this,
                        null
                );
                if (!t.asElement().getModifiers().contains(Modifier.STATIC)) {
                    throw new RuntimeException(String.format(
                            "EMCMod must be instantiable via .newInstance(): inner class %s must be declared static.",
                            t.toString()
                    ));
                }
            }
            if (!t.asElement().getModifiers().contains(Modifier.PUBLIC)) {
                throw new RuntimeException(String.format(
                        "EMCMod must be instantiable via .newInstance(): inner class %s must be declared public.",
                        t.toString()
                ));
            }
            return null;
        }
    };
    private TypeVisitor<Boolean, Void> noArgsVisitor = new SimpleTypeVisitor8<Boolean, Void>() {
        @Override
        public Boolean visitExecutable(final ExecutableType t,
                                       final Void aVoid) {
            return t.getParameterTypes().isEmpty();
        }
    };
    private final TypeVisitor<Void, Void> classValidatingVisitor = new SimpleTypeVisitor8<Void, Void>() {
        @Override
        public Void visitDeclared(final DeclaredType t,
                                  final Void aVoid) {
            if (t.asElement().getEnclosingElement().getKind().isClass()) {
                t.asElement().getEnclosingElement().asType().accept(
                        checkInternalClassForStatic,
                        null
                );
            }
            if (!t.asElement().getModifiers().contains(Modifier.PUBLIC)) {
                throw new RuntimeException(String.format(
                        "EMCMod must be instantiable via .newInstance(): inner class %s must be declared public.",
                        t.toString()
                ));
            }
            if (t.asElement().getEnclosedElements().stream().filter(subelement -> subelement.getKind() == CONSTRUCTOR && subelement.getModifiers().contains(PUBLIC)).map(Element::asType).anyMatch(typeMirror -> typeMirror.accept(
                    noArgsVisitor,
                    null
            ))) {
                return null;
            } else {
                throw new RuntimeException("EMCMod must be instantiable via .newInstance(): EMCMods must define publicly accessible no args constructor.");
            }
        }
    };
    private TypeVisitor<String, Void> nameFetchingVisitor = new SimpleTypeVisitor8<String, Void>() {
        @Override
        public String visitDeclared(final DeclaredType t,
                                    final Void aVoid) {
            return t.toString();
        }
    };

    @Override
    public boolean process(final Set<? extends TypeElement> annotations,
                           final RoundEnvironment roundEnv) {
        boolean alreadyProcessed = false;
        if (!roundEnv.processingOver()) {
            for (TypeElement type : annotations) {
                for (Element element : roundEnv.getElementsAnnotatedWith(type)) {
                    if (alreadyProcessed) {
                        throw new RuntimeException("Too many ModInfo annotations for one given library.");
                    }
                    ModInfo modInfo = element.getAnnotation(ModInfo.class);
                    checkValid(modInfo);
                    String json = fromAnnotation(modInfo);
                    try (
                            Writer writer = processingEnv.getFiler().createResource(
                                    StandardLocation.CLASS_OUTPUT,
                                    "",
                                    "client.json"
                            ).openWriter()
                    ) {
                        writer.write(json);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    alreadyProcessed = true;
                }
            }
            if (!alreadyProcessed) {
                throw new RuntimeException("Must have one class declared with ModInfo (preferably, an implementation of EMCMod).");
            }
        }
        return true;
    }

    private void checkValid(final ModInfo annotation) {
        Arrays.asList(
                annotation.author(),
                annotation.minversion(),
                annotation.name(),
                annotation.version(),
                annotation.website()
        ).forEach(Objects::requireNonNull);
        try {
            annotation.main();
        } catch (MirroredTypeException mte) {
            mte.getTypeMirror().accept(
                    classValidatingVisitor,
                    null
            );
        }
    }

    private String fromAnnotation(final ModInfo annotation) {
        return String.format(
                "{\n    \"name\":\"%s\",\n    \"website\":\"%s\",\n    \"author\":\"%s\",\n    \"minversion\":%s,\n    \"version\":%s,\n    \"main\":\"%s\",\n    \"updateLinkOverride\":%s\n}\n",
                annotation.name(),
                annotation.website(),
                annotation.author(),
                annotation.minversion(),
                annotation.version(),
                getModName(annotation),
                annotation.updateLinkOverride()
        );
    }

    private String getModName(final ModInfo annotation) {
        try {
            annotation.main();
        } catch (MirroredTypeException mte) {
            return mte.getTypeMirror().accept(
                    nameFetchingVisitor,
                    null
            );
        }
        return null;
    }
}
