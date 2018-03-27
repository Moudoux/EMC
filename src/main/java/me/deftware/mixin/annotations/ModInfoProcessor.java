package me.deftware.mixin.annotations;

import com.google.gson.Gson;

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
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;

import static javax.lang.model.SourceVersion.RELEASE_8;
import static javax.lang.model.element.ElementKind.CONSTRUCTOR;
import static javax.lang.model.element.Modifier.PUBLIC;

@SupportedAnnotationTypes("me.deftware.mixin.annotations.ModInfo")
@SupportedSourceVersion(RELEASE_8)
public class ModInfoProcessor extends AbstractProcessor {
    private final Gson gson = new Gson();
    private int count = 0;
    private final TypeVisitor<Void, ModInfoInstantiable> classNameWritingVisitor = new SimpleTypeVisitor8<Void, ModInfoInstantiable>() {
        @Override
        public Void visitDeclared(final DeclaredType t,
                                  final ModInfoInstantiable modInfoInstantiable) {
            modInfoInstantiable.setMain(t.toString());
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
    private final TypeVisitor<Void, Void> checkInternalClassForStatic = new SimpleTypeVisitor8<Void, Void>() {
        @Override
        public Void visitDeclared(final DeclaredType t,
                                  final Void aVoid) {
            if (t.asElement().getEnclosingElement().getKind().isClass()) {
                t.asElement().getEnclosingElement().asType().accept(
                        this,
                        null
                );
            }
            if (!t.asElement().getModifiers().contains(Modifier.STATIC)) {
                throw new RuntimeException("EMCMod must be instantiable via .newInstance(): inner class must be declared static.");
            }
            return null;
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
        if (!roundEnv.processingOver()) {
            for (TypeElement type : annotations) {
                for (Element element : roundEnv.getElementsAnnotatedWith(type)) {
                    ModInfo modInfo = element.getAnnotation(ModInfo.class);
                    if (isValid(modInfo)) {
                        ModInfoInstantiable instantiable = fromAnnotation(modInfo);
                        try {
                            gson.toJson(
                                    instantiable,
                                    processingEnv.getFiler().createResource(
                                            StandardLocation.CLASS_OUTPUT,
                                            "",
                                            "modinfo_" + getModName(modInfo)
                                    ).openWriter()
                            );
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        count++;
                    }
                }
            }
            if (count == 0) {
                throw new RuntimeException("Must have one class declared with ModInfo (preferably, an implementation of EMCMod).");
            }
        }
        return true;
    }

    private ModInfoInstantiable fromAnnotation(final ModInfo annotation) {
        ModInfoInstantiable instantiable = new ModInfoInstantiable();
        instantiable.setAuthor(annotation.author());
        try {
            annotation.main();
        } catch (MirroredTypeException mte) {
            mte.getTypeMirror().accept(
                    classNameWritingVisitor,
                    instantiable
            );
        }
        return null;
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

    private boolean isValid(final ModInfo annotation) {
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
        return false;
    }
}
