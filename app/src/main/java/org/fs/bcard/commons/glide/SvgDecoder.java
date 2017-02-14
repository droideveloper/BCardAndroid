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

import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.resource.SimpleResource;
import com.caverock.androidsvg.SVG;
import com.caverock.androidsvg.SVGParseException;
import java.io.IOException;
import java.io.InputStream;

public class SvgDecoder implements ResourceDecoder<InputStream, SVG> {

  @Override public Resource<SVG> decode(InputStream source, int width, int height)
      throws IOException {
    try {
      SVG svg = SVG.getFromInputStream(source);
      return new SimpleResource<>(svg);
    } catch (SVGParseException error) {
      throw new IOException("can not convert to SVG", error);
    }
  }

  @Override public String getId() {
    return "SvgDecoder.org.fs.bcard.commons.glide";
  }
}
