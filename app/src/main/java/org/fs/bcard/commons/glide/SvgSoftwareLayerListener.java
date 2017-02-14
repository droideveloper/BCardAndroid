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
package org.fs.bcard.commons.glide;

import android.graphics.drawable.PictureDrawable;
import android.widget.ImageView;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.ImageViewTarget;
import com.bumptech.glide.request.target.Target;

public class SvgSoftwareLayerListener<T> implements RequestListener<T, PictureDrawable> {

  @Override public boolean onException(Exception e, T model, Target<PictureDrawable> target,
      boolean isFirstResource) {
    ImageView view = ((ImageViewTarget<?>) target).getView();
    view.setLayerType(ImageView.LAYER_TYPE_NONE, null);
    return false;
  }

  @Override public boolean onResourceReady(PictureDrawable resource, T model, Target<PictureDrawable> target,
      boolean isFromMemoryCache, boolean isFirstResource) {
    ImageView view = ((ImageViewTarget<?>) target).getView();
    view.setLayerType(ImageView.LAYER_TYPE_SOFTWARE, null);
    return false;
  }
}
