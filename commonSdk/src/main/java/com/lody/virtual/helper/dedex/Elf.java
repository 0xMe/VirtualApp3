/*
 * Decompiled with CFR 0.152.
 */
package com.lody.virtual.helper.dedex;

import com.lody.virtual.StringFog;
import com.lody.virtual.helper.dedex.DataReader;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;

public class Elf
implements Closeable {
    static final char[] ELF_MAGIC;
    static final int EI_CLASS = 4;
    static final int EI_DATA = 5;
    static final int EI_NIDENT = 16;
    final char[] e_ident = new char[16];
    public static final String SHN_DYNSYM;
    public static final String SHN_DYNSTR;
    public static final String SHN_HASH;
    public static final String SHN_RODATA;
    public static final String SHN_TEXT;
    public static final String SHN_DYNAMIC;
    public static final String SHN_SHSTRTAB;
    static final int SHN_UNDEF = 0;
    static final int SHT_PROGBITS = 1;
    static final int SHT_SYMTAB = 2;
    static final int SHT_STRTAB = 3;
    static final int SHT_RELA = 4;
    static final int SHT_HASH = 5;
    static final int SHT_DYNAMIC = 6;
    static final int SHT_DYNSYM = 11;
    static final int PT_NULL = 0;
    static final int PT_LOAD = 1;
    static final int PT_DYNAMIC = 2;
    static final int PT_INTERP = 3;
    static final int PT_NOTE = 4;
    static final int PT_SHLIB = 5;
    static final int PT_PHDR = 6;
    static final int PT_TLS = 7;
    static final int PF_X = 1;
    static final int PF_W = 2;
    static final int PF_R = 4;
    static final int PF_MASKOS = 0xFF00000;
    static final int PF_MASKPROC = -268435456;
    private final DataReader mReader;
    private final Ehdr mHeader;
    private final Elf_Shdr[] mSectionHeaders;
    private byte[] mStringTable;
    public final boolean is64bit;
    boolean mReadFull;
    Elf_Phdr[] mProgHeaders;
    Elf_Sym[] mDynamicSymbols;
    byte[] mDynStringTable;

    final boolean checkMagic() {
        return this.e_ident[0] == ELF_MAGIC[0] && this.e_ident[1] == ELF_MAGIC[1] && this.e_ident[2] == ELF_MAGIC[2] && this.e_ident[3] == ELF_MAGIC[3];
    }

    final char getFileClass() {
        return this.e_ident[4];
    }

    final char getDataEncoding() {
        return this.e_ident[5];
    }

    public final boolean isLittleEndian() {
        return this.getDataEncoding() == '\u0001';
    }

    public DataReader getReader() {
        return this.mReader;
    }

    public Ehdr getHeader() {
        return this.mHeader;
    }

    public Elf_Shdr[] getSectionHeaders() {
        return this.mSectionHeaders;
    }

    public Elf(String file, boolean closeNow) throws Exception {
        this(file);
        if (closeNow) {
            this.mReader.close();
        }
    }

    public Elf(String file) throws Exception {
        this(new File(file));
    }

    public Elf(File file) throws Exception {
        Elf_Shdr strSec;
        Ehdr header;
        DataReader r = this.mReader = new DataReader(file);
        r.readBytes(this.e_ident);
        if (!this.checkMagic()) {
            throw new IOException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAgcLmsVHi9iVyQ/Khc9Om8jQS1qATMxPQhSVg==")) + file);
        }
        r.setLittleEndian(this.isLittleEndian());
        boolean bl = this.is64bit = this.getFileClass() == '\u0002';
        if (this.is64bit) {
            header = new Elf64_Ehdr();
            header.e_type = r.readShort();
            header.e_machine = r.readShort();
            header.e_version = r.readInt();
            header.e_entry = r.readLong();
            header.e_phoff = r.readLong();
            header.e_shoff = r.readLong();
            this.mHeader = header;
        } else {
            header = new Elf32_Ehdr();
            ((Elf32_Ehdr)header).e_type = r.readShort();
            ((Elf32_Ehdr)header).e_machine = r.readShort();
            ((Elf32_Ehdr)header).e_version = r.readInt();
            ((Elf32_Ehdr)header).e_entry = r.readInt();
            ((Elf32_Ehdr)header).e_phoff = r.readInt();
            ((Elf32_Ehdr)header).e_shoff = r.readInt();
            this.mHeader = header;
        }
        Ehdr h = this.mHeader;
        h.e_flags = r.readInt();
        h.e_ehsize = r.readShort();
        h.e_phentsize = r.readShort();
        h.e_phnum = r.readShort();
        h.e_shentsize = r.readShort();
        h.e_shnum = r.readShort();
        h.e_shstrndx = r.readShort();
        this.mSectionHeaders = new Elf_Shdr[h.e_shnum];
        for (int i = 0; i < h.e_shnum; ++i) {
            Elf_Shdr secHeader;
            long offset = h.getSectionOffset() + (long)(i * h.e_shentsize);
            r.seek(offset);
            if (this.is64bit) {
                secHeader = new Elf64_Shdr();
                secHeader.sh_name = r.readInt();
                secHeader.sh_type = r.readInt();
                secHeader.sh_flags = r.readLong();
                secHeader.sh_addr = r.readLong();
                secHeader.sh_offset = r.readLong();
                secHeader.sh_size = r.readLong();
                secHeader.sh_link = r.readInt();
                secHeader.sh_info = r.readInt();
                secHeader.sh_addralign = r.readLong();
                secHeader.sh_entsize = r.readLong();
                this.mSectionHeaders[i] = secHeader;
                continue;
            }
            secHeader = new Elf32_Shdr();
            ((Elf32_Shdr)secHeader).sh_name = r.readInt();
            ((Elf32_Shdr)secHeader).sh_type = r.readInt();
            ((Elf32_Shdr)secHeader).sh_flags = r.readInt();
            ((Elf32_Shdr)secHeader).sh_addr = r.readInt();
            ((Elf32_Shdr)secHeader).sh_offset = r.readInt();
            ((Elf32_Shdr)secHeader).sh_size = r.readInt();
            ((Elf32_Shdr)secHeader).sh_link = r.readInt();
            ((Elf32_Shdr)secHeader).sh_info = r.readInt();
            ((Elf32_Shdr)secHeader).sh_addralign = r.readInt();
            ((Elf32_Shdr)secHeader).sh_entsize = r.readInt();
            this.mSectionHeaders[i] = secHeader;
        }
        if (h.e_shstrndx > -1 && h.e_shstrndx < this.mSectionHeaders.length) {
            strSec = this.mSectionHeaders[h.e_shstrndx];
            if (strSec.sh_type != 3) {
                throw new IOException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IS0MD2ojPyhhJwoqKQcYM34wAitoJCwaLD4bJGILAjZvESw9LBgqIG5TElo=")) + h.e_shstrndx);
            }
        } else {
            throw new IOException(StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAgcLmsVHi9iVyQ/Ji4qMmoKBgRlNywzOBhSVg==")) + h.e_shstrndx);
        }
        int strSecSize = strSec.getSize();
        this.mStringTable = new byte[strSecSize];
        r.seek(strSec.getOffset());
        r.readBytes(this.mStringTable);
        if (this.mReadFull) {
            this.readSymbolTables();
            this.readProgramHeaders();
        }
    }

    private void readSymbolTables() {
        DataReader r = this.mReader;
        Elf_Shdr dynsym = this.getSection(SHN_DYNSYM);
        if (dynsym != null) {
            r.seek(dynsym.getOffset());
            int len = dynsym.getSize() / (this.is64bit ? 24 : 16);
            this.mDynamicSymbols = new Elf_Sym[len];
            char[] cbuf = new char[1];
            for (int i = 0; i < len; ++i) {
                Elf_Sym dsym;
                if (this.is64bit) {
                    dsym = new Elf64_Sym();
                    dsym.st_name = r.readInt();
                    r.readBytes(cbuf);
                    dsym.st_info = cbuf[0];
                    r.readBytes(cbuf);
                    dsym.st_other = cbuf[0];
                    dsym.st_value = r.readLong();
                    dsym.st_size = r.readLong();
                    dsym.st_shndx = r.readShort();
                    this.mDynamicSymbols[i] = dsym;
                    continue;
                }
                dsym = new Elf32_Sym();
                ((Elf32_Sym)dsym).st_name = r.readInt();
                ((Elf32_Sym)dsym).st_value = r.readInt();
                ((Elf32_Sym)dsym).st_size = r.readInt();
                r.readBytes(cbuf);
                ((Elf32_Sym)dsym).st_info = cbuf[0];
                r.readBytes(cbuf);
                ((Elf32_Sym)dsym).st_other = cbuf[0];
                ((Elf32_Sym)dsym).st_shndx = r.readShort();
                this.mDynamicSymbols[i] = dsym;
            }
            Elf_Shdr dynLinkSec = this.mSectionHeaders[dynsym.sh_link];
            r.seek(dynLinkSec.getOffset());
            this.mDynStringTable = new byte[dynLinkSec.getSize()];
            r.readBytes(this.mDynStringTable);
        }
    }

    private void readProgramHeaders() {
        Ehdr h = this.mHeader;
        DataReader r = this.mReader;
        this.mProgHeaders = new Elf_Phdr[h.e_phnum];
        for (int i = 0; i < h.e_phnum; ++i) {
            Elf_Phdr progHeader;
            long offset = h.getProgramOffset() + (long)(i * h.e_phentsize);
            r.seek(offset);
            if (this.is64bit) {
                progHeader = new Elf64_Phdr();
                progHeader.p_type = r.readInt();
                progHeader.p_offset = r.readInt();
                progHeader.p_vaddr = r.readLong();
                progHeader.p_paddr = r.readLong();
                progHeader.p_filesz = r.readLong();
                progHeader.p_memsz = r.readLong();
                progHeader.p_flags = r.readLong();
                progHeader.p_align = r.readLong();
                this.mProgHeaders[i] = progHeader;
                continue;
            }
            progHeader = new Elf32_Phdr();
            ((Elf32_Phdr)progHeader).p_type = r.readInt();
            ((Elf32_Phdr)progHeader).p_offset = r.readInt();
            ((Elf32_Phdr)progHeader).p_vaddr = r.readInt();
            ((Elf32_Phdr)progHeader).p_paddr = r.readInt();
            ((Elf32_Phdr)progHeader).p_filesz = r.readInt();
            ((Elf32_Phdr)progHeader).p_memsz = r.readInt();
            ((Elf32_Phdr)progHeader).p_flags = r.readInt();
            ((Elf32_Phdr)progHeader).p_align = r.readInt();
            this.mProgHeaders[i] = progHeader;
        }
    }

    public final Elf_Shdr getSection(String name) {
        for (Elf_Shdr sec : this.mSectionHeaders) {
            if (!name.equals(this.getString(sec.sh_name))) continue;
            return sec;
        }
        return null;
    }

    public final Elf_Sym getSymbolTable(String name) {
        if (this.mDynamicSymbols != null) {
            for (Elf_Sym sym : this.mDynamicSymbols) {
                if (!name.equals(this.getDynString(sym.st_name))) continue;
                return sym;
            }
        }
        return null;
    }

    public final String getString(int index) {
        if (index == 0) {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IixfU2Y2NABqHDAU"));
        }
        int end = index;
        while (this.mStringTable[end] != 0) {
            ++end;
        }
        return new String(this.mStringTable, index, end - index);
    }

    public final String getDynString(int index) {
        if (index == 0) {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IixfU2Y2NABqHDAU"));
        }
        int end = index;
        while (this.mDynStringTable[end] != 0) {
            ++end;
        }
        return new String(this.mDynStringTable, index, end - index);
    }

    @Override
    public void close() {
        this.mReader.close();
    }

    static {
        SHN_DYNSYM = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Mz4qJ2ogLD9gAVRF"));
        SHN_DYNSTR = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Mz4qJ2ogLAZhN1RF"));
        SHN_HASH = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Mz5fP28zRVo="));
        SHN_RODATA = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Mz0MD2gFJAZ9AVRF"));
        SHN_TEXT = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Mz0qM2kKMFo="));
        SHN_DYNAMIC = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Mz4qJ2ojJCNjDihF"));
        SHN_SHSTRTAB = StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Mz02Cm8wMARmHiA6"));
        ELF_MAGIC = new char[]{'\u007f', 'E', 'L', 'F'};
    }

    static class Elf64_Phdr
    extends Elf_Phdr {
        long p_vaddr;
        long p_paddr;
        long p_filesz;
        long p_memsz;
        long p_flags;
        long p_align;

        Elf64_Phdr() {
        }

        @Override
        public long getFlags() {
            return this.p_flags;
        }
    }

    static class Elf32_Phdr
    extends Elf_Phdr {
        int p_vaddr;
        int p_paddr;
        int p_filesz;
        int p_memsz;
        int p_flags;
        int p_align;

        Elf32_Phdr() {
        }

        @Override
        public long getFlags() {
            return this.p_flags;
        }
    }

    static abstract class Elf_Phdr {
        int p_type;
        int p_offset;

        Elf_Phdr() {
        }

        abstract long getFlags();

        String flagsString() {
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PBhSVg==")) + ((this.getFlags() & 4L) != 0L ? StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Ij5SVg==")) : StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Jy5SVg=="))) + ((this.getFlags() & 2L) != 0L ? StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IS5SVg==")) : StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Jy5SVg=="))) + ((this.getFlags() & 1L) != 0L ? StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IBhSVg==")) : StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Jy5SVg=="))) + StringFog.decrypt(com.kook.librelease.StringFog.decrypt("PAhSVg=="));
        }

        String programType() {
            switch (this.p_type) {
                case 0: {
                    return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("OzsuQGIFSFo="));
                }
                case 1: {
                    return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("OxgAP2gFJCpgHjM8Oy0MM28jGiZvEVRF"));
                }
                case 2: {
                    return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JRcYCGsVEi99ICQPKAc6D2kjMAY="));
                }
                case 3: {
                    return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("JAgcLGgaFgJhNDAgKAgtOmIzQQZqEVRF"));
                }
                case 4: {
                    return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("Oz4ALGgVSFo="));
                }
                case 5: {
                    return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IhUqH2cxRQ5rDCxF"));
                }
                case 6: {
                    return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IhcMD2gwFjdgCiQKKAciPmkgRVo="));
                }
            }
            return StringFog.decrypt(com.kook.librelease.StringFog.decrypt("IQgcMWojGj1gMCQPKAcqLmwjNCY="));
        }
    }

    static class Elf64_Sym
    extends Elf_Sym {
        long st_value;
        long st_size;

        Elf64_Sym() {
        }

        @Override
        long getSize() {
            return this.st_size;
        }
    }

    static class Elf32_Sym
    extends Elf_Sym {
        int st_value;
        int st_size;

        Elf32_Sym() {
        }

        @Override
        long getSize() {
            return this.st_size;
        }
    }

    public static abstract class Elf_Sym {
        int st_name;
        char st_info;
        char st_other;
        short st_shndx;

        char getBinding() {
            return (char)(this.st_info >> 4);
        }

        char getType() {
            return (char)(this.st_info & 0xF);
        }

        void setBinding(char b) {
            this.setBindingAndType(b, this.getType());
        }

        void setType(char t) {
            this.setBindingAndType(this.getBinding(), t);
        }

        void setBindingAndType(char b, char t) {
            this.st_info = (char)((b << 4) + (t & 0xF));
        }

        abstract long getSize();

        public long getOffset(Elf elf) {
            for (int i = 0; i < elf.mSectionHeaders.length; ++i) {
                if (this.st_shndx != i) continue;
                return elf.mSectionHeaders[i].getOffset();
            }
            return -1L;
        }
    }

    static class Elf64_Shdr
    extends Elf_Shdr {
        long sh_flags;
        long sh_addr;
        long sh_offset;
        long sh_size;
        long sh_addralign;
        long sh_entsize;

        Elf64_Shdr() {
        }

        @Override
        public int getSize() {
            return (int)this.sh_size;
        }

        @Override
        public long getOffset() {
            return this.sh_offset;
        }
    }

    static class Elf32_Shdr
    extends Elf_Shdr {
        int sh_flags;
        int sh_addr;
        int sh_offset;
        int sh_size;
        int sh_addralign;
        int sh_entsize;

        Elf32_Shdr() {
        }

        @Override
        public int getSize() {
            return this.sh_size;
        }

        @Override
        public long getOffset() {
            return this.sh_offset;
        }
    }

    public static abstract class Elf_Shdr {
        int sh_name;
        int sh_type;
        int sh_link;
        int sh_info;

        public abstract int getSize();

        public abstract long getOffset();
    }

    static class Elf64_Ehdr
    extends Ehdr {
        long e_entry;
        long e_phoff;
        long e_shoff;

        Elf64_Ehdr() {
        }

        @Override
        long getSectionOffset() {
            return this.e_shoff;
        }

        @Override
        long getProgramOffset() {
            return this.e_phoff;
        }
    }

    static class Elf32_Ehdr
    extends Ehdr {
        int e_entry;
        int e_phoff;
        int e_shoff;

        Elf32_Ehdr() {
        }

        @Override
        long getSectionOffset() {
            return this.e_shoff;
        }

        @Override
        long getProgramOffset() {
            return this.e_phoff;
        }
    }

    public static abstract class Ehdr {
        short e_type;
        short e_machine;
        int e_version;
        int e_flags;
        short e_ehsize;
        short e_phentsize;
        short e_phnum;
        short e_shentsize;
        short e_shnum;
        short e_shstrndx;

        abstract long getSectionOffset();

        abstract long getProgramOffset();
    }
}

