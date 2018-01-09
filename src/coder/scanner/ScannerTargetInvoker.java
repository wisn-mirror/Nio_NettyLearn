package coder.scanner;

import coder.annotion.SocketCmd;
import coder.annotion.SocketModule;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Component
public class ScannerTargetInvoker implements BeanPostProcessor {
    @Nullable
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Nullable
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        scanner(bean, beanName);
        return bean;
    }

    public void scanner(Object object, String beanName) {
        Class<? extends Object> clazz = object.getClass();
        Class<?>[] interfaces = clazz.getInterfaces();
        if (interfaces != null && interfaces.length > 0) {
            for (Class<?> one : interfaces) {
                SocketModule socketModule = one.getAnnotation(SocketModule.class);
                if (socketModule == null) {
                    continue;
                }
                Method[] methods = one.getMethods();
                if (methods != null && methods.length > 0) {
                    for (Method method : methods) {
                        SocketCmd socketCmd = method.getAnnotation(SocketCmd.class);
                        if (socketCmd == null) {
                            continue;
                        }
                        short module = socketModule.module();
                        short cmd = socketCmd.cmd();
                        Invoker invoker = InvokerHolder.getInvoker(module, cmd);
                        if (invoker == null) {
                            InvokerHolder.addInvoker(module, cmd, Invoker.valueOf(method, cmd));
                        } else {
                            System.out.println("重复beanName:" + beanName + " module" + module + " cmd" + cmd);
                        }
                    }
                }
            }
        }
    }
}
