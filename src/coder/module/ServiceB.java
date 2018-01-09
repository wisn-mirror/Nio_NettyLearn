package coder.module;

import coder.annotion.SocketCmd;
import coder.annotion.SocketModule;

@SocketModule(module = ModuleId.module2)
public interface ServiceB {
    @SocketCmd(cmd = CmdId.cmd1)
    void printServiceB1();

    @SocketCmd(cmd = CmdId.cmd2)
    void printServiceB2();
}
