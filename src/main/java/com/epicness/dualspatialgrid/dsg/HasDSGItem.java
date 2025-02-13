package com.epicness.dualspatialgrid.dsg;

import com.epicness.dualspatialgrid.utils.BooleanConsumer;
import com.epicness.dualspatialgrid.utils.FloatFloatBiConsumer;

public interface HasDSGItem {

    DSGItem getDSGItem();

    default void setTranslationListener(FloatFloatBiConsumer consumer) {
        getDSGItem().translationListener = consumer;
    }

    default void setGridAListener(BooleanConsumer consumer) {
        getDSGItem().gridAListener = consumer;
    }
}
