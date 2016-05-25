package net.sf.jabref.logic.formatter.bibtexfields;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Tests in addition to the general tests from {@link net.sf.jabref.logic.formatter.FormatterTest}
 */
public class RemoveLatexFormatterTest {

    private final RemoveLatexFormatter formatter = new RemoveLatexFormatter();

    @Test
    public void test() {
        assertEquals("$\\alpha\\beta$", formatter.format("$\\alpha$$\\beta$"));
    }

    @Test
    public void formatExample() {
        assertEquals("Nucleus API", formatter.format(formatter.getExampleInput()));
    }


}
