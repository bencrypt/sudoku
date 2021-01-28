package utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class LanguageBundle {

    private static ResourceBundle bundle;

    public static ResourceBundle getBundle() {

        // when the bundle did not exist, create it
        if (bundle == null) {
            bundle = initResourceBundle();
        }

        // return existing or created bundle
        return bundle;
    }

    /**
     * Loads the correct resource bundle for internationalization.
     */
    private static ResourceBundle initResourceBundle() {

        // location of language bundle within "resources"
        String bundlesPkg = "bundles.language";

        // testing method for english strings
        //Locale.setDefault(new Locale("en"));

        // check if German is set to system language
        boolean germanAsSystemLanguage = Locale.getDefault().getLanguage().equals(new Locale("de").getLanguage());

        // load German package (if system is set so), else the default package
        if (germanAsSystemLanguage) {
            return ResourceBundle.getBundle(bundlesPkg, new Locale("de"));
        } else {
            return ResourceBundle.getBundle(bundlesPkg);
        }
    }
}
