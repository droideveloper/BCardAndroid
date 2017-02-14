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
package org.fs.bcard.commons.modules;

import android.graphics.drawable.PictureDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import dagger.Module;
import dagger.Provides;
import java.io.InputStream;
import org.fs.bcard.commons.glide.SvgDecoder;
import org.fs.bcard.commons.glide.SvgDrawableTranscoder;
import org.fs.bcard.commons.glide.SvgSoftwareLayerListener;
import org.fs.bcard.viewmodels.MainActivityViewModel;
import org.fs.bcard.views.MainActivityView;
import org.fs.mvvm.data.IView;
import org.fs.mvvm.injections.ForActivity;

@Module
public class ActivityModule {

  @ForActivity @Provides
  public GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> provideRequestBuilder(AppCompatActivity activity) {
    return Glide.with(activity)
        .using(Glide.buildStreamModelLoader(Uri.class, activity), InputStream.class)
        .from(Uri.class)
        .as(SVG.class)
        .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
        .sourceEncoder(new StreamEncoder())
        .cacheDecoder(new FileToStreamDecoder<>(new SvgDecoder()))
        .decoder(new SvgDecoder())
        .listener(new SvgSoftwareLayerListener<>());
  }


  @ForActivity @Provides public MainActivityViewModel provideMainActivityViewModel(IView view, GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> requestBuilder) {
    return new MainActivityViewModel((MainActivityView) view, requestBuilder);
  }
}
