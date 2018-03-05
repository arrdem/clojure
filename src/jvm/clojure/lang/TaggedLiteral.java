/**
 *   Copyright (c) Rich Hickey. All rights reserved.
 *   The use and distribution terms for this software are covered by the
 *   Eclipse Public License 1.0 (http://opensource.org/licenses/eclipse-1.0.php)
 *   which can be found in the file epl-v10.html at the root of this distribution.
 *   By using this software in any fashion, you are agreeing to be bound by
 * 	 the terms of this license.
 *   You must not remove this notice, or any other, from this software.
 **/

package clojure.lang;

public class TaggedLiteral extends ATaggedLiteral {

public final Symbol tag;
public final Object form;

public static TaggedLiteral create(Symbol tag, Object form) {
	return new TaggedLiteral(tag, form);
}

private TaggedLiteral(Symbol tag, Object form){
	this.tag = tag;
	this.form = form;
}

public Symbol getTag() {
    return this.tag;
}

public Object getForm() {
    return this.form;
}

@Override
public boolean equals(Object o) {
	if (this == o) return true;
	// FIXME: what of other ITaggedLiterals?
	if (o == null || getClass() != o.getClass()) return false;

	TaggedLiteral that = (TaggedLiteral) o;

	if (form != null ? !form.equals(that.form) : that.form != null) return false;
	if (tag != null ? !tag.equals(that.tag) : that.tag != null) return false;

	return true;
}

}
