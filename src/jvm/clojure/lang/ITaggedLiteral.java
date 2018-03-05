/**
 *   Copyright (c) Rich Hickey. All rights reserved.
 *   The use and distribution terms for this software are covered by the
 *   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 *   which can be found in the file epl-v10.html at the root of this distribution.
 *   By using this software in any fashion, you are agreeing to be bound by
 *   the terms of this license.
 *   You must not remove this notice, or any other, from this software.
 **/

package clojure.lang;

public interface ITaggedLiteral extends ILookup {

public static final Keyword TAG_KW = Keyword.intern("tag");
public static final Keyword FORM_KW = Keyword.intern("form");

public Symbol getTag();
public Object getForm();

public default Object valAt(Object key) {
  return valAt(key, null);
}

public default Object valAt(Object key, Object notFound) {
  if (FORM_KW.equals(key)) {
    return this.getForm();
  } else if (TAG_KW.equals(key)) {
    return this.getTag();
  } else {
    return notFound;
  }
}

}
