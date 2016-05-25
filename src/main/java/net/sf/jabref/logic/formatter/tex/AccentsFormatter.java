package net.sf.jabref.logic.formatter.tex;

import net.sf.jabref.logic.formatter.Formatter;
import net.sf.jabref.logic.l10n.Localization;

public class AccentsFormatter implements Formatter {

    @Override
    public String getName() {
        return Localization.lang("LaTeX remover");
    }

    @Override
    public String getKey() {
        return "remove_latex";
    }

    @Override
    public String format(String oldString) {
        String newValue = oldString;
        // https://en.wikibooks.org/wiki/LaTeX/Special_Characters
        // https://github.com/plk/biber/blob/dev/lib/Biber/LaTeX/recode_data.xml
        // ftp://sunsite.icm.edu.pl/pub/CTAN/info/symbols/comprehensive/symbols-a4.pdf
        // Remove commands, e.g., \command[]{}, \command, \command{}
        // Remove math, e.g., $...$
        // Remove special syntax, e.g., ~
        // braces in general {}
        // https://wch.github.io/latexsheet/latexsheet.pdf


        // Remove redundant $, {, and }, but not if the } is part of a command argument: \mbox{-}{GPS} should not be adjusted
        newValue = newValue.replace("$$", "").replaceAll("(?<!\\\\[\\p{Alpha}]{0,100}\\{[^\\}]{0,100})\\}([-/ ]?)\\{",
                "$1");
        // Move numbers, +, -, /, and brackets into equations
        newValue = newValue.replaceAll("(([^$]|\\\\\\$)*)\\$", "$1@@"); // Replace $, but not \$ with @@
        newValue = newValue.replaceAll("([^@]*)@@([^@]*)@@", "$1\\$$2@@"); // Replace every other @@ with $
        //newValue = newValue.replaceAll("([0-9\\(\\.]+) \\$","\\$$1\\\\ "); // Move numbers followed by a space left of $ inside the equation, e.g., 0.35 $\mu$m
        newValue = newValue.replaceAll("([0-9\\(\\.]+[ ]?[-+/]?[ ]?)\\$", "\\$$1"); // Move numbers, possibly with operators +, -, or /,  left of $ into the equation
        newValue = newValue.replaceAll("@@([ ]?[-+/]?[ ]?[0-9\\)\\.]+)", " $1@@"); // Move numbers right of @@ into the equation
        newValue = newValue.replace("@@", "$"); // Replace all @@ with $
        newValue = newValue.replace("  ", " "); // Clean up
        newValue = newValue.replace("$$", "");
        newValue = newValue.replace(" )$", ")$");
        return newValue;
    }

    @Override
    public String getDescription() {
        return Localization.lang("Removes LaTeX code.");
    }

    @Override
    public String getExampleInput() {
        return "\textit{Nucleus} {API}";
    }

}
