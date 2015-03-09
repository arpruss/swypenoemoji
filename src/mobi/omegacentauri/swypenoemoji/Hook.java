package mobi.omegacentauri.swypenoemoji;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.content.res.XResources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.view.InputDevice;
import android.view.KeyEvent;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XC_MethodHook.MethodHookParam;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class Hook implements IXposedHookLoadPackage {
	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		if (!lpparam.packageName.startsWith("com.nuance.swype"))
			return;
		
		findAndHookMethod("android.content.res.Resources", lpparam.classLoader,
				"getBoolean", int.class, new XC_MethodHook() {
			@Override
			protected void afterHookedMethod(MethodHookParam param) throws Throwable {
				if ((Boolean)param.getResult() && 
						((Resources)param.thisObject).getResourceEntryName(
								(Integer)param.args[0]).equals("enable_emoji_in_english_ldb")) 
					param.setResult(false);
			}
			protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
			}
		});
	}
}
