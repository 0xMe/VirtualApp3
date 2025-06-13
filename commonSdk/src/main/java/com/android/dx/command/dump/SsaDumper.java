/*
 * Decompiled with CFR 0.152.
 */
package com.android.dx.command.dump;

import com.android.dx.cf.code.ConcreteMethod;
import com.android.dx.cf.code.Ropper;
import com.android.dx.cf.iface.Member;
import com.android.dx.cf.iface.Method;
import com.android.dx.command.dump.Args;
import com.android.dx.command.dump.BlockDumper;
import com.android.dx.rop.code.AccessFlags;
import com.android.dx.rop.code.DexTranslationAdvice;
import com.android.dx.rop.code.RopMethod;
import com.android.dx.ssa.Optimizer;
import com.android.dx.ssa.SsaBasicBlock;
import com.android.dx.ssa.SsaInsn;
import com.android.dx.ssa.SsaMethod;
import com.android.dx.util.ByteArray;
import com.android.dx.util.Hex;
import com.android.dx.util.IntList;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumSet;

public class SsaDumper
extends BlockDumper {
    public static void dump(byte[] bytes, PrintStream out, String filePath, Args args) {
        SsaDumper sd = new SsaDumper(bytes, out, filePath, args);
        sd.dump();
    }

    private SsaDumper(byte[] bytes, PrintStream out, String filePath, Args args) {
        super(bytes, out, filePath, true, args);
    }

    @Override
    public void endParsingMember(ByteArray bytes, int offset, String name, String descriptor, Member member) {
        if (!(member instanceof Method)) {
            return;
        }
        if (!this.shouldDumpMethod(name)) {
            return;
        }
        if ((member.getAccessFlags() & 0x500) != 0) {
            return;
        }
        ConcreteMethod meth = new ConcreteMethod((Method)member, this.classFile, true, true);
        DexTranslationAdvice advice = DexTranslationAdvice.THE_ONE;
        RopMethod rmeth = Ropper.convert(meth, advice, this.classFile.getMethods(), this.dexOptions);
        SsaMethod ssaMeth = null;
        boolean isStatic = AccessFlags.isStatic(meth.getAccessFlags());
        int paramWidth = SsaDumper.computeParamWidth(meth, isStatic);
        if (this.args.ssaStep == null) {
            ssaMeth = Optimizer.debugNoRegisterAllocation(rmeth, paramWidth, isStatic, true, advice, EnumSet.allOf(Optimizer.OptionalStep.class));
        } else if ("edge-split".equals(this.args.ssaStep)) {
            ssaMeth = Optimizer.debugEdgeSplit(rmeth, paramWidth, isStatic, true, advice);
        } else if ("phi-placement".equals(this.args.ssaStep)) {
            ssaMeth = Optimizer.debugPhiPlacement(rmeth, paramWidth, isStatic, true, advice);
        } else if ("renaming".equals(this.args.ssaStep)) {
            ssaMeth = Optimizer.debugRenaming(rmeth, paramWidth, isStatic, true, advice);
        } else if ("dead-code".equals(this.args.ssaStep)) {
            ssaMeth = Optimizer.debugDeadCodeRemover(rmeth, paramWidth, isStatic, true, advice);
        }
        StringBuilder sb = new StringBuilder(2000);
        sb.append("first ");
        sb.append(Hex.u2(ssaMeth.blockIndexToRopLabel(ssaMeth.getEntryBlockIndex())));
        sb.append('\n');
        ArrayList<SsaBasicBlock> blocks = ssaMeth.getBlocks();
        ArrayList sortedBlocks = (ArrayList)blocks.clone();
        Collections.sort(sortedBlocks, SsaBasicBlock.LABEL_COMPARATOR);
        for (SsaBasicBlock block : sortedBlocks) {
            sb.append("block ").append(Hex.u2(block.getRopLabel())).append('\n');
            BitSet preds = block.getPredecessors();
            int i = preds.nextSetBit(0);
            while (i >= 0) {
                sb.append("  pred ");
                sb.append(Hex.u2(ssaMeth.blockIndexToRopLabel(i)));
                sb.append('\n');
                i = preds.nextSetBit(i + 1);
            }
            sb.append("  live in:" + block.getLiveInRegs());
            sb.append("\n");
            for (SsaInsn insn : block.getInsns()) {
                sb.append("  ");
                sb.append(insn.toHuman());
                sb.append('\n');
            }
            if (block.getSuccessors().cardinality() == 0) {
                sb.append("  returns\n");
            } else {
                int primary = block.getPrimarySuccessorRopLabel();
                IntList succLabelList = block.getRopLabelSuccessorList();
                int szSuccLabels = succLabelList.size();
                for (int i2 = 0; i2 < szSuccLabels; ++i2) {
                    sb.append("  next ");
                    sb.append(Hex.u2(succLabelList.get(i2)));
                    if (szSuccLabels != 1 && primary == succLabelList.get(i2)) {
                        sb.append(" *");
                    }
                    sb.append('\n');
                }
            }
            sb.append("  live out:" + block.getLiveOutRegs());
            sb.append("\n");
        }
        this.suppressDump = false;
        this.parsed(bytes, 0, bytes.size(), sb.toString());
        this.suppressDump = true;
    }
}

