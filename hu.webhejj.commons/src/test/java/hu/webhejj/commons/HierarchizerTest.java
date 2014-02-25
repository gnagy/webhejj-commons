/*
 *  Copyright Gergely Nagy <greg@webhejj.hu>
 *
 *  Licensed under the Apache License, Version 2.0;
 *  you may obtain a copy of the License at:
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 */
package hu.webhejj.commons;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class HierarchizerTest {

    @Test
    public void testHierarchizer() {

        final StringBuilder buf = new StringBuilder();
        Hierarchizer<String> hierarchizer = new Hierarchizer<String>() {
            @Override
            protected List<String> getSegments(String item) {
                return Arrays.asList(item.split("/"));
            }

            @Override
            protected void onPush(Stack<String> stack, String segment, String item) {
                buf.append("<");
                buf.append(segment);
                buf.append(">");
            }

            @Override
            protected void onPop(Stack<String> stack, String segment) {
                buf.append("</");
                buf.append(segment);
                buf.append(">");
            }
        };
        hierarchizer.hierarchize(Arrays.asList("foo/bar/baz", "foo/bar/qux"));
        Assert.assertEquals("Hierarchized string", "<foo><bar><baz></baz><qux></qux></bar></foo>", buf.toString());
    }
}
