/*
 * Copyright (c) 2018, 2020, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */

package gc.nvdimm;

/* @test TestAllocateOldGenAt.java
 * @key gc
 * @summary Test to check allocation of Java Heap with AllocateOldGenAt option
 * @requires vm.gc=="null" & os.family != "aix"
 * @requires test.vm.gc.nvdimm
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 * @run driver gc.nvdimm.TestAllocateOldGenAt
 */

import jdk.test.lib.JDKToolFinder;
import jdk.test.lib.process.ProcessTools;
import jdk.test.lib.process.OutputAnalyzer;
import java.util.ArrayList;
import java.util.Collections;

public class TestAllocateOldGenAt {
  private static String[] commonFlags;

  public static void main(String args[]) throws Exception {
    String test_dir = System.getProperty("test.dir", ".");
    commonFlags = new String[] {
        "-XX:+UnlockExperimentalVMOptions",
        "-XX:AllocateOldGenAt=" + test_dir,
        "-Xmx32m",
        "-Xms32m",
        "-version"};

    runTest("-XX:+UseG1GC");
    runTest("-XX:+UseParallelGC");
  }

  private static void runTest(String... extraFlags) throws Exception {
    ArrayList<String> flags = new ArrayList<>();
    Collections.addAll(flags, commonFlags);
    Collections.addAll(flags, extraFlags);
    ProcessBuilder pb = ProcessTools.createJavaProcessBuilder(true, flags);
    OutputAnalyzer output = new OutputAnalyzer(pb.start());

    output.shouldHaveExitValue(0);

  }
}
