/*
 * BCard Copyright (C) 2017 Fatih.
 *  
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.fs.bcard.views;

import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.view.View;
import javax.inject.Inject;
import org.fs.bcard.BuildConfig;
import org.fs.bcard.R;
import org.fs.bcard.BR;
import org.fs.bcard.commons.components.DaggerActivityComponent;
import org.fs.bcard.commons.modules.ActivityModule;
import org.fs.bcard.viewmodels.MainActivityViewModel;
import org.fs.mvvm.common.AbstractActivity;
import org.fs.mvvm.injections.AbstractActivityModule;
import org.fs.mvvm.utils.Objects;

public final class MainActivity extends AbstractActivity<MainActivityViewModel>
    implements MainActivityView {

  @Inject MainActivityViewModel viewModel;
  @Inject ViewDataBinding viewDataBinding;

  @Override public void onCreate(Bundle restoreState) {
    super.onCreate(restoreState);
    DaggerActivityComponent.builder()
        .activityModule(new ActivityModule())
        .abstractActivityModule(new AbstractActivityModule(this, R.layout.view_main_activity))
        .build()
        .inject(this);
    viewDataBinding.setVariable(BR.viewModel, viewModel);
    viewModel.restoreState(restoreState != null ? restoreState : getIntent().getExtras());
    viewModel.onCreate();
  }

  @Override public void onSaveInstanceState(Bundle storeState) {
    super.onSaveInstanceState(storeState);
    viewModel.storeState(storeState);
  }

  @Override public void onStart() {
    super.onStart();
    viewModel.onStart();
  }

  @Override public void onStop() {
    viewModel.onStop();
    super.onStop();
  }

  @Override public void showError(String errorString) {
    final View view = view();
    if (!Objects.isNullOrEmpty(view)) {
      Snackbar.make(view, errorString, Snackbar.LENGTH_LONG).show();
    }
  }

  @Override public void showError(String errorString, String actionTextString,
      View.OnClickListener callback) {
    final View view = view();
    if (!Objects.isNullOrEmpty(view)) {
      final Snackbar snackbar = Snackbar.make(view, errorString, Snackbar.LENGTH_LONG);
      snackbar.setAction(actionTextString, v -> {
        if (callback != null) {
          callback.onClick(v);
        }
        snackbar.dismiss();
      });
      snackbar.show();
    }
  }

  @Override public void finish() {
    super.finish();
    overridePendingTransition(R.anim.activity_anim_translate_right_in, R.anim.activity_anim_scale_out);
  }

  @Override public String getStringResource(@StringRes int stringId) {
    return getString(stringId);
  }

  @Override public Context getContext() {
    return this;
  }

  @Override public boolean isAvailable() {
    return !isFinishing();
  }

  @Override protected boolean isLogEnabled() {
    return BuildConfig.DEBUG;
  }

  @Override protected String getClassTag() {
    return MainActivity.class.getSimpleName();
  }

  private View view() {
    return viewDataBinding != null ? viewDataBinding.getRoot() : null;
  }
}