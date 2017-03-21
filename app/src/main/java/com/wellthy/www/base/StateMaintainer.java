package com.wellthy.www.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.wellthy.www.BuildConfig;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Retains and maintain object's state between configuration changes in Activities and Fragments
 *
 * Created by jimitpatel on 05/01/17.
 *
 * <a href="http://www.tinmegali.com">www.tinmegali.com</a>
 * Based on <a href="https://github.com/douglascraigschmidt/POSA-15/tree/master/ex/AcronymExpander/src/vandy/mooc">
 *     framework MVP</a> developed by
 * <a href="https://github.com/douglascraigschmidt">
 *     Dr. Douglas Schmidth</a>
 *
 * @see <a href="https://github.com/tinmegali/simple-mvp">Project's Git</a> <br>
 * @see <a href="https://github.com/tinmegali/simple-mvp/tree/master/AndroidMVP/app">Sample Application</a>
 * @see <a href="https://github.com/tinmegali/simple-mvp/blob/master/AndroidMVP/app/src/main/java/com/tinmegali/androidmvp/main/MVP_MainActivity.java">
 *         Sample MVP interface
 *     </a>
 */

public class StateMaintainer {

    private static final String TAG = StateMaintainer.class.getSimpleName();

    private final String mStateMaintainerTag;
    private final WeakReference<FragmentManager> mFragmentManager;
    private StateMaintainerFragment mStateMaintainerFragment;
    private boolean isRecreating;

    public StateMaintainer(String mStateMaintainerTag, FragmentManager fragmentManager) {
        this.mStateMaintainerTag = mStateMaintainerTag;
        this.mFragmentManager =  new WeakReference<>(fragmentManager);
    }

    /**
     * Creates the Fragment responsible to maintain the objects.
     * @return true: fragment just created
     */
    public boolean firstTimeIn() {
        try {
            // Retreiving reference
            mStateMaintainerFragment = (StateMaintainerFragment) mFragmentManager.get().findFragmentByTag(mStateMaintainerTag);

            // Creating new retained fragment
            if (null == mStateMaintainerFragment) {
                if (BuildConfig.DEBUG)
                    Log.i(TAG, "Creating New RetainedFragment " + mStateMaintainerTag);
                mStateMaintainerFragment = new StateMaintainerFragment();
                mFragmentManager.get().beginTransaction().add(mStateMaintainerFragment, mStateMaintainerTag).commit();
                isRecreating = false;
                return true;
            } else {
                if (BuildConfig.DEBUG)
                    Log.i(TAG, "Returning retained existing fragment " + mStateMaintainerTag);
                isRecreating = true;
                return false;
            }
        } catch (NullPointerException e) {
            if (BuildConfig.DEBUG)
                e.printStackTrace();
            return false;
        }
    }

    /**
     * Return <strong>true</strong> it the current Activity was recreated at least one time
     * @return If the Activity was recreated
     */
    public boolean wasCreated() {
        return isRecreating;
    }

    /**
     * Inserts the object to be preserved
     * @param key       object's TAG
     * @param object    object to maintain
     */
    public void put(String key, Object object) {
        mStateMaintainerFragment.put(key, object);
    }

    /**
     * Inserts the object to be preserved
     * @param object    object to maintain
     */
    public void put(Object object) {
        put(object.getClass().getName(), object);
    }

    /**
     * Recovers the object saved
     * @param key   Object's TAG
     * @param <T>   Object type
     * @return      Object saved
     */
    @SuppressWarnings("unchecked")
    public <T> T get(String key)  {
        return mStateMaintainerFragment.get(key);

    }

    /**
     * Checks the existence of a given object
     * @param key   Key to verification
     * @return      true: Object exists
     */
    public boolean hasKey(String key) {
        return mStateMaintainerFragment.get(key) != null;
    }

    /**
     * Fragment resposible to preserve objects.
     * Instantiated only once. Uses a hashmap to save objects
     */
    public static class StateMaintainerFragment extends Fragment {
        private HashMap<String, Object> mData = new HashMap<>();

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            // Grants that fragment will be preserved
            setRetainInstance(true);
        }

        /**
         * Insert objects on the hashmap
         * @param key       Reference key
         * @param object    Object to be saved
         */
        public void put(String key, Object object) {
            mData.put(key, object);
        }

        /**
         * Insert objects on the hashmap
         * @param object    object to be saved
         */
        public void put(Object object) {
            put(object.getClass().getName(), object);
        }

        /**
         * Recovers saved object
         * @param key   Reference key
         * @param <T>   Object type
         * @return      Object saved
         */
        @SuppressWarnings("unchecked")
        public <T>T get(String key) {
            return (T) mData.get(key);
        }
    }
}
