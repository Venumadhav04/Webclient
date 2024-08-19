import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.software.os.OperatingSystem;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SystemInfoController {

    @GetMapping("/system-info")
    public Map<String, Object> getSystemInfo() {
        Map<String, Object> systemInfo = new HashMap<>();

        // OS and System Information
        SystemInfo si = new SystemInfo();
        OperatingSystem os = si.getOperatingSystem();
        GlobalMemory memory = si.getHardware().getMemory();
        CentralProcessor processor = si.getHardware().getProcessor();

        systemInfo.put("OS Name", os.toString());
        systemInfo.put("OS Version", os.getVersionInfo().getVersion());
        systemInfo.put("OS Architecture", processor.getProcessorIdentifier().getMicroarchitecture());

        // Java and JVM Information
        String javaVersion = System.getProperty("java.version");
        systemInfo.put("Java Version", javaVersion);

        Runtime runtime = Runtime.getRuntime();
        systemInfo.put("JVM Free Memory (MB)", runtime.freeMemory() / (1024 * 1024));
        systemInfo.put("JVM Total Memory (MB)", runtime.totalMemory() / (1024 * 1024));

        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryMXBean.getHeapMemoryUsage();
        systemInfo.put("Heap Memory Used (MB)", heapMemoryUsage.getUsed() / (1024 * 1024));
        systemInfo.put("Heap Memory Max (MB)", heapMemoryUsage.getMax() / (1024 * 1024));

        // Host Name
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            systemInfo.put("Host Name", localhost.getHostName());
        } catch (UnknownHostException e) {
            systemInfo.put("Host Name", "Unknown");
        }

        // RAM Usage
        systemInfo.put("Total RAM (GB)", memory.getTotal() / (1024 * 1024 * 1024));
        systemInfo.put("Available RAM (GB)", memory.getAvailable() / (1024 * 1024 * 1024));
        systemInfo.put("Used RAM (GB)", (memory.getTotal() - memory.getAvailable()) / (1024 * 1024 * 1024));

        return systemInfo;
    }
}
