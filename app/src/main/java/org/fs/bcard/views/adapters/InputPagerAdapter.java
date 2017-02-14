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
package org.fs.bcard.views.adapters;

import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import org.fs.bcard.BR;
import org.fs.bcard.BuildConfig;
import org.fs.bcard.R;
import org.fs.bcard.entities.InputEntity;
import org.fs.bcard.views.viewholders.InputViewHolder;
import org.fs.mvvm.common.PagerBindingAdapter;

public final class InputPagerAdapter extends PagerBindingAdapter<InputEntity, InputViewHolder> {

  public InputPagerAdapter(ObservableList<InputEntity> dataSource) {
    super(dataSource);
  }

  @Override protected InputViewHolder createViewHolder(ViewDataBinding binding, int viewType) {
    return new InputViewHolder(binding);
  }

  @Override
  protected void bindViewHolder(InputEntity item, InputViewHolder viewHolder, int position) {
    viewHolder.setDataAndSync(BR.item, item, position);
  }

  @LayoutRes @Override protected int layoutResource(int viewType) {
    return R.layout.view_text_input_layout;
  }

  @Override protected int getItemViewType(int position) {
    return 0;
  }

  @Override protected boolean isLogEnabled() {
    return BuildConfig.DEBUG;
  }

  @Override protected String getClassTag() {
    return InputPagerAdapter.class.getSimpleName();
  }
}