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
package org.fs.bcard.entities;

import android.databinding.Bindable;
import android.os.Parcel;
import android.text.InputType;
import android.view.inputmethod.EditorInfo;
import org.fs.bcard.BR;
import org.fs.bcard.BuildConfig;
import org.fs.mvvm.common.AbstractEntity;
import org.fs.mvvm.utils.Objects;

public final class InputEntity extends AbstractEntity {

  private int inputType   = InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES
      | InputType.TYPE_TEXT_FLAG_AUTO_CORRECT | InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE;

  private int imeOptions  = EditorInfo.IME_ACTION_NEXT;
  private String text;
  private String hint;
  private boolean state;

  public InputEntity() { /*default constructor if you need to use it via reflection such as Gson uses.*/ }

  private InputEntity(int inputType, int imeOptions, String text, String hint, boolean state) {
    this.inputType = inputType;
    this.imeOptions = imeOptions;
    this.text = text;
    this.hint = hint;
    this.state = state;
  }
  private InputEntity(Parcel input) {
    super(input);
  }

  @Override protected void readParcel(Parcel input) {
    inputType = input.readInt();
    imeOptions = input.readInt();
    boolean hasText = input.readInt() == 1;
    if (hasText) {
      text = input.readString();
    }
    boolean hasHint = input.readInt() == 1;
    if (hasHint) {
      hint = input.readString();
    }
    state = input.readInt() == 1;
  }

  @Override public void writeToParcel(Parcel out, int flags) {
    out.writeInt(inputType);
    out.writeInt(imeOptions);
    boolean hasText = !Objects.isNullOrEmpty(text);
    out.writeInt(hasText ? 1 : 0);
    if (hasText) {
      out.writeString(text);
    }
    boolean hasHint = !Objects.isNullOrEmpty(hint);
    out.writeInt(hasHint ? 1 : 0);
    if (hasHint) {
      out.writeString(hint);
    }
    out.writeInt(state ? 1 : 0);
  }

  @Bindable public int getInputType() {
    return this.inputType;
  }

  @Bindable public int getImeOptions() {
    return this.imeOptions;
  }

  @Bindable public String getText() {
    return this.text;
  }

  public void setText(String text) {
    if (text.equalsIgnoreCase(this.text)) {
      this.text = text;
      notifyPropertyChanged(BR.text);
    }
  }

  public void setState(boolean state) {
    if (state != this.state) {
      this.state = state;
      notifyPropertyChanged(BR.state);
    }
  }

  @Bindable public boolean getState() {
    return this.state;
  }

  @Bindable public String getHint() {
    return this.hint;
  }

  @Override public int describeContents() {
    return 0;
  }

  @Override protected String getClassTag() {
    return InputEntity.class.getSimpleName();
  }

  @Override protected boolean isLogEnabled() {
    return BuildConfig.DEBUG;
  }

  public Builder newBuilder() {
    return new Builder()
       .inputType(inputType)
       .imeOptions(imeOptions)
       .text(text)
       .hint(hint)
       .state(state);
  }

  public final static Creator<InputEntity> CREATOR = new Creator<InputEntity>() {

    @Override public InputEntity createFromParcel(Parcel input) {
      return new InputEntity(input);
    }

    @Override public InputEntity[] newArray(int size) {
      return new InputEntity[size];
    }
  };

  public static class Builder {
    private int inputType;
    private int imeOptions;
    private String text;
    private String hint;
    private boolean state;

    public Builder() { }
    public Builder inputType(int inputType) { this.inputType = inputType; return this; }
    public Builder imeOptions(int imeOptions) { this.imeOptions = imeOptions; return this; }
    public Builder text(String text) { this.text = text; return this; }
    public Builder hint(String hint) { this.hint = hint; return this; }
    public Builder state(boolean state) { this.state = state; return this; }

    public InputEntity build() {
      return new InputEntity(inputType, imeOptions, text, hint, state);
    }
  }
}