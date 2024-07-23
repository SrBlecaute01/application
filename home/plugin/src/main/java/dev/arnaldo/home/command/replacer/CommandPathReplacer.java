package dev.arnaldo.home.command.replacer;

import dev.arnaldo.home.command.annotation.CommandPath;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.dynamic.AnnotationReplacer;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.Collection;

@RequiredArgsConstructor
public class CommandPathReplacer implements AnnotationReplacer<CommandPath> {

    private final FileCommandReplacer replacer;

    @Override @Nullable @SneakyThrows
    public Collection<Annotation> replaceAnnotations(@NotNull AnnotatedElement element, @NotNull CommandPath annotation) {
        final var file = StringUtils.isBlank(annotation.file()) ? "config.yml" : annotation.file();
        final var path = annotation.value();

        return replacer.replace(file, path, Command.class);
    }

}