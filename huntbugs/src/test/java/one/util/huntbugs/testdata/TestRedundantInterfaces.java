/*
 * Copyright 2016 HuntBugs contributors
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package one.util.huntbugs.testdata;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;
import java.util.RandomAccess;
import java.util.Set;
import java.util.TreeSet;

import one.util.huntbugs.registry.anno.AssertNoWarning;
import one.util.huntbugs.registry.anno.AssertWarning;

/**
 * @author Tagir Valeev
 *
 */
@AssertWarning("RedundantInterface")
public class TestRedundantInterfaces extends TreeSet<String> implements Set<String>, Collection<String> {
    private static final long serialVersionUID = 1L;

    @AssertNoWarning("RedundantInterface")
    public static class NoRedundantInterfaces extends LinkedList<String> implements RandomAccess, Serializable {
        private static final long serialVersionUID = 1L;
    }
    
    @AssertNoWarning("RedundantInterface")
    public static class NoRedundantInterfaces2 extends NoRedundantInterfaces implements Serializable {
        private static final long serialVersionUID = 1L;
    }
}
