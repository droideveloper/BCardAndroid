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

import android.graphics.Picture;
import android.graphics.drawable.PictureDrawable;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.SimpleResource;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;
import com.caverock.androidsvg.SVG;

public class SvgDrawableTranscoder implements ResourceTranscoder<SVG, PictureDrawable> {

  @Override public Resource<PictureDrawable> transcode(Resource<SVG> to) {
    SVG svg = to.get();
    Picture picture = svg.renderToPicture();
    PictureDrawable drawable = new PictureDrawable(picture);
    return new SimpleResource<>(drawable);
  }

  @Override public String getId() {
    return "SvgDrawableTranscoder.org.fs.bcard.commons.glide";
  }
}
