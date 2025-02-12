package com.epicness.dualspatialgrid.dsg;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public interface HasDSGObject {

    DSGObject getDSGObject();

    default void setTranslationListener(BiConsumer<Float, Float> consumer) {
        getDSGObject().translationListener = consumer;
    }

    default void setGridAListener(Consumer<Boolean> consumer) {
        getDSGObject().gridAListener = consumer;
    }
}
