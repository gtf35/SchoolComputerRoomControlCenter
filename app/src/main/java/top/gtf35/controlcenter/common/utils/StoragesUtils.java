package top.gtf35.controlcenter.common.utils;

import android.content.Context;
import android.os.storage.StorageManager;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class StoragesUtils {
    public static class StorageInfo {
        public String path;
        public String state;
        public boolean isRemoveable;
        public StorageInfo(String path) {
            this.path = path;
        }
        public boolean isMounted() {
            return "mounted".equals(state);
        }
        @Override
        public String toString() {
            return "StorageInfo [path=" + path + ", state=" + state
                    + ", isRemoveable=" + isRemoveable + "]";
        }
    }
    public static List<StorageInfo> listAllStorage(Context context) {
        ArrayList<StorageInfo> storages = new ArrayList<StorageInfo>();
        StorageManager storageManager = (StorageManager) context.getSystemService(Context.STORAGE_SERVICE);
        try {
            Class<?>[] paramClasses = {};
            Method getVolumeList = StorageManager.class.getMethod("getVolumeList", paramClasses);
            Object[] params = {};
            Object[] invokes = (Object[]) getVolumeList.invoke(storageManager, params);

            if (invokes != null) {
                StorageInfo info = null;
                for (int i = 0; i < invokes.length; i++) {
                    Object obj = invokes[i];
                    Method getPath = obj.getClass().getMethod("getPath", new Class[0]);
                    String path = (String) getPath.invoke(obj, new Object[0]);
                    info = new StorageInfo(path);

                    Method getVolumeState = StorageManager.class.getMethod("getVolumeState", String.class);
                    String state = (String) getVolumeState.invoke(storageManager, info.path);
                    info.state = state;

                    Method isRemovable = obj.getClass().getMethod("isRemovable", new Class[0]);
                    info.isRemoveable = ((Boolean) isRemovable.invoke(obj, new Object[0])).booleanValue();
                    storages.add(info);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        storages.trimToSize();
        return storages;
    }

    public static List<StorageInfo> getAvaliableStorage(List<StorageInfo> infos){
        List<StorageInfo> storages = new ArrayList<StorageInfo>();
        for(StorageInfo info : infos){
            File file = new File(info.path);
            if ((file.exists()) && (file.isDirectory()) && (file.canWrite())) {
                if (info.isMounted()) {
                    storages.add(info);
                }
            }
        }

        return storages;
    }
}
