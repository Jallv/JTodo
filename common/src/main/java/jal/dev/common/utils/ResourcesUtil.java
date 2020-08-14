package jal.dev.common.utils;

/**
 * Description: <动态获取资源id><br>
 * Author: mxdl<br>
 * Date: 2018/6/19<br>
 * Version: V1.0.0<br>
 * Update: <br>
 */


public class ResourcesUtil {
    public static String getString(int res) {
        return ContextProvider.getContext().getResources().getString(res);
    }

    public static String getString(int res, Object... objects) {
       return ContextProvider.getContext().getResources().getString(res, objects);
    }

    /**
     * 获取id
     *
     * @param resName 资源名称
     * @return 资源id
     */
    public static int id(String resName) {
        return ContextProvider.getContext().getResources().getIdentifier(resName, "id", ContextProvider.getContext().getPackageName());
    }

    /**
     * 获取anim类型资源id
     *
     * @param resName 资源名称
     * @return 资源id
     */
    public static int anim(String resName) {
        return ContextProvider.getContext().getResources().getIdentifier(resName, "anim", ContextProvider.getContext().getPackageName());
    }

    /**
     * 获取layout类型资源id
     *
     * @param resName 资源名称
     * @return 资源id
     */
    public static int layout(String resName) {
        return ContextProvider.getContext().getResources().getIdentifier(resName, "layout", ContextProvider.getContext().getPackageName());
    }

    /**
     * 获取drawable类型资源id
     *
     * @param resName 资源名称
     * @return 资源id
     */
    public static int drawable(String resName) {
        return ContextProvider.getContext().getResources().getIdentifier(resName, "drawable", ContextProvider.getContext().getPackageName());
    }

    /**
     * 获取string类型资源id
     *
     * @param resName 资源名称
     * @return 资源id
     */
    public static int string(String resName) {
        return ContextProvider.getContext().getResources().getIdentifier(resName, "string", ContextProvider.getContext().getPackageName());
    }

    /**
     * 获取raw类型资源id
     *
     * @param resName 资源名称
     * @return 资源id
     */
    public static int raw(String resName) {
        return ContextProvider.getContext().getResources().getIdentifier(resName, "raw", ContextProvider.getContext().getPackageName());
    }

    /**
     * 获取style类型资源id
     *
     * @param resName 资源名称
     * @return 资源id
     */
    public static int style(String resName) {
        return ContextProvider.getContext().getResources().getIdentifier(resName, "style", ContextProvider.getContext().getPackageName());
    }
}