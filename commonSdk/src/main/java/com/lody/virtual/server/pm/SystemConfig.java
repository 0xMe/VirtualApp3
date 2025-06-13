/*
 * Decompiled with CFR 0.152.
 */
package com.lody.virtual.server.pm;

import com.lody.virtual.StringFog;
import com.lody.virtual.helper.utils.VLog;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class SystemConfig {
    private static final String TAG = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ii0YKWwFNCNlJB42KD0cMw=="));
    private final Map<String, SharedLibraryEntry> mSharedLibraries = new HashMap<String, SharedLibraryEntry>();

    public void load() {
        long beforeTime = System.currentTimeMillis();
        File permissionsDir = new File(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("My4uLGs3GgJiASw3KQgqL2wjNCZsIwZF")));
        this.readSharedLibraries(permissionsDir);
        long costTime = System.currentTimeMillis() - beforeTime;
        VLog.e(TAG, StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IxgAP2gJIClgJyggPxg2MW8jBTJ4EVRF")) + costTime + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Iwc1CA==")));
    }

    public SharedLibraryEntry getSharedLibrary(String name) {
        return this.mSharedLibraries.get(name);
    }

    /*
     * Exception decompiling
     */
    private void readPermissionsFromXml(File permFile) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$TooOptimisticMatchException
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.getString(SwitchStringRewriter.java:404)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.access$600(SwitchStringRewriter.java:53)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$SwitchStringMatchResultCollector.collectMatches(SwitchStringRewriter.java:368)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.ResetAfterTest.match(ResetAfterTest.java:24)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.KleeneN.match(KleeneN.java:24)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.MatchSequence.match(MatchSequence.java:26)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.matchutil.ResetAfterTest.match(ResetAfterTest.java:23)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.rewriteComplex(SwitchStringRewriter.java:201)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter.rewrite(SwitchStringRewriter.java:73)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:881)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:531)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    public void readSharedLibraries(File permissionsDir) {
        File[] permissionFiles = permissionsDir.listFiles();
        if (permissionFiles == null) {
            return;
        }
        for (File permissionFile : permissionFiles) {
            if (!permissionFile.isFile() || !permissionFile.getName().endsWith(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Mz1fDWoFSFo=")))) continue;
            this.readPermissionsFromXml(permissionFile);
        }
    }

    public static class SharedLibraryEntry {
        public String name;
        public String path;
        public String[] dependencies;

        public SharedLibraryEntry(String name, String path, String[] dependencies) {
            this.name = name;
            this.path = path;
            this.dependencies = dependencies;
        }
    }
}

