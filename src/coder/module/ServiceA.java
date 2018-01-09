package coder.module;

import coder.annotion.SocketCmd;
import coder.annotion.SocketModule;

@SocketModule(module = ModuleId.module1)
public interface ServiceA {
    @SocketCmd(cmd = CmdId.cmd1)
    void printServiceA1();

    @SocketCmd(cmd = CmdId.cmd2)
    void printServiceA2();
}
