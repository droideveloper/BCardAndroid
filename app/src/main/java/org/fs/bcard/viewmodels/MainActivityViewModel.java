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
package org.fs.bcard.viewmodels;

import android.animation.Animator;
import android.content.ContentResolver;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.net.Uri;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.animation.Interpolator;
import android.view.inputmethod.EditorInfo;
import com.bumptech.glide.GenericRequestBuilder;
import java.io.InputStream;
import org.fs.bcard.BR;
import org.fs.bcard.BuildConfig;
import org.fs.bcard.R;
import org.fs.bcard.entities.InputEntity;
import org.fs.bcard.views.MainActivityView;
import org.fs.bcard.views.adapters.InputPagerAdapter;
import org.fs.mvvm.commands.RelayCommand;
import org.fs.mvvm.data.AbstractViewModel;
import org.fs.mvvm.listeners.OnPageSelected;
import org.fs.mvvm.listeners.SimpleAnimatorListener;


public final class MainActivityViewModel extends AbstractViewModel<MainActivityView> {

  public final int menuResource = R.menu.reset_menu;

  public final OnPageSelected onPageSelected = (page) -> {
    this.selectedPage = page;
    for (int index = 0, z = this.dataSource.size(); index < z; index++) {
      final InputEntity entity = this.dataSource.get(index);
      if (index == page) {
        entity.setState(true);
      } else {
        entity.setState(false);
      }
    }
    updateProgressUI();
  };

  public final RelayCommand exitNavigation = new RelayCommand(MainActivityViewModel.this::onBackPressed);

  public final Toolbar.OnMenuItemClickListener menuCallback = (item) -> {
    if (item.getItemId() == R.id.action_reset) {
      this.displayState = !this.displayState;

      this.frontAnimator = this.displayState ? R.animator.flip_in : R.animator.flip_out;
      this.backAnimator = this.displayState ? R.animator.flip_out : R.animator.flip_in;

      setBackElevation(0);
      setFrontElevation(0);

      notifyPropertyChanged(BR.displayState);
      notifyPropertyChanged(BR.frontAnimator);
      notifyPropertyChanged(BR.backAnimator);
      return true;
    }
    return false;
  };

  public final SimpleAnimatorListener animatorListener = new SimpleAnimatorListener() {

    @Override public void onAnimationEnd(Animator animation) {
      if (displayState) {
        setFrontElevation(12);
      } else {
        setBackElevation(12);
      }
      frontAnimator = 0;
      backAnimator = 0;
    }
  };

  public final Interpolator sharedInterpolator = (t) -> {
    t -= 1.0f;
    return t * t * t * t * t + 1.0f;
  };

  private PagerAdapter itemSource;
  private int frontAnimator;
  private int frontElevation;
  private String frontUri;
  private int backAnimator;
  private int backElevation;
  private String backUri;
  private boolean displayState;
  private int selectedPage;
  private int progress;
  private GenericRequestBuilder<Uri, InputStream, ?, ?> requestBuilder;

  private ObservableList<InputEntity> dataSource;

  public MainActivityViewModel(MainActivityView view, GenericRequestBuilder<Uri, InputStream, ?, ?> requestBuilder) {
    super(view);
    this.requestBuilder = requestBuilder;
    dataSource = new ObservableArrayList<>();
    itemSource = new InputPagerAdapter(dataSource);
    frontUri = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + view.getPackageName() + "/" + R.raw.front;
    backUri = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + view.getPackageName() + "/" + R.raw.back;
    frontElevation = 0;
    selectedPage = 0;
    backElevation = 0;

    dataSource.add(new InputEntity.Builder()
      .inputType(InputType.TYPE_CLASS_NUMBER)
      .imeOptions(EditorInfo.IME_ACTION_NEXT)
      .hint("PLEASE ENTER CARD NUMBER")
      .state(true)
      .build());

    dataSource.add(new InputEntity.Builder()
      .inputType(InputType.TYPE_CLASS_DATETIME)
      .imeOptions(EditorInfo.IME_ACTION_NEXT)
      .hint("VALID DATE")
      .state(false)
      .build());

    dataSource.add(new InputEntity.Builder()
      .inputType(InputType.TYPE_CLASS_TEXT)
      .imeOptions(EditorInfo.IME_ACTION_NEXT)
      .hint("CARD OWNER")
      .state(false)
      .build());
  }

  @Override public void onStart() {
    updateProgressUI();
  }

  @Override public void onStop()  {}

  @Bindable public PagerAdapter getItemSource() {
    return this.itemSource;
  }

  @Bindable public int getFrontElevation() {
    return this.frontElevation;
  }

  public void setFrontElevation(int frontElevation) {
    if (this.frontElevation != frontElevation) {
      this.frontElevation = frontElevation;
      notifyPropertyChanged(BR.frontElevation);
    }
  }

  @Bindable public int getBackElevation() {
    return this.backElevation;
  }

  public void setBackElevation(int backElevation) {
    if (this.backElevation != backElevation) {
      this.backElevation = backElevation;
      notifyPropertyChanged(BR.backElevation);
    }
  }

  @Bindable public int getFrontAnimator() {
    return this.frontAnimator;
  }

  @Bindable public int getBackAnimator() {
    return this.backAnimator;
  }

  @Bindable public boolean getDisplayState() {
    return this.displayState;
  }

  @Bindable public String getFrontUri() {
    return this.frontUri;
  }

  @Bindable public String getBackUri() {
    return this.backUri;
  }

  @Bindable public GenericRequestBuilder<Uri, InputStream, ?, ?> getRequestBuilder() {
    return this.requestBuilder;
  }

  @Bindable public int getProgress() {
    return this.progress;
  }

  public void setProgress(int progress) {
    if (this.progress != progress) {
      this.progress = progress;
      notifyPropertyChanged(BR.progress);
    }
  }

  @Bindable public int getSelectedPage() {
    return this.selectedPage;
  }

  public void setSelectedPage(int selectedPage) {
    if (this.selectedPage != selectedPage) {
      this.selectedPage = selectedPage;
      notifyPropertyChanged(BR.selectedPage);
    }
  }

  @Override public void onBackPressed() {
    if (view.isAvailable()) {
      view.finish();
    }
  }

  @Override protected boolean isLogEnabled() {
    return BuildConfig.DEBUG;
  }

  @Override protected String getClassTag() {
    return MainActivityViewModel.class.getSimpleName();
  }

  private void updateProgressUI() {
    float division = (1.0f * (selectedPage + 1)) / ( 1.0f * this.dataSource.size());
    setProgress(Math.round(100 * division));
  }
}