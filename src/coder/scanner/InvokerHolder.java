package coder.scanner;

import java.util.HashMap;
import java.util.LinkedHashMap;

public class InvokerHolder {
    public static HashMap<Short, HashMap<Short, Invoker>> invokers = new LinkedHashMap<>();

    public static void addInvoker(short module, short cmd, Invoker invoker) {
        HashMap<Short, Invoker> shortInvokerHashMap = invokers.get(module);
        if (shortInvokerHashMap == null) {
            shortInvokerHashMap = new HashMap<>();
        }
        shortInvokerHashMap.put(cmd, invoker);
    }

    public static Invoker getInvoker(short module, short cmd) {
        HashMap<Short, Invoker> shortInvokerHashMap = invokers.get(module);
        if (shortInvokerHashMap == null) return null;
        return shortInvokerHashMap.get(cmd);
    }

}
