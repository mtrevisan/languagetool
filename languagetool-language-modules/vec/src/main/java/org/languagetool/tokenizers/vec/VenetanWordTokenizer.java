/* LanguageTool, a natural language style checker
 * Copyright (C) 2013 Marcin Milkowski (http://www.languagetool.org)
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301
 * USA
 */
package org.languagetool.tokenizers.vec;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RegExUtils;
import org.languagetool.tokenizers.WordTokenizer;

/**
 * @author Mauro Trevisan
 */
public class VenetanWordTokenizer extends WordTokenizer {

  private static final String UNICODE_APOSTROPHE = "'";
  private static final String UNICODE_MODIFIER_LETTER_APOSTROPHE = "\u02BC";
  private static final String UNICODE_APOSTROPHES = UNICODE_APOSTROPHE + UNICODE_MODIFIER_LETTER_APOSTROPHE;

  private final Pattern patternApostrophe;


  public VenetanWordTokenizer(){
    final String quotedTokenizingChars = Pattern.quote(getTokenizingCharacters());
    patternApostrophe = Pattern.compile("(?i)"
      + "(a[lƚnv]|di|e[lƚn]|[gks][oó]|[iu]n|[lƚ][aài]|v[aàeèéiíoòóuú])[" + UNICODE_APOSTROPHES + "](?=[" + quotedTokenizingChars + "]|$)"
      + "|"
      + "[" + UNICODE_APOSTROPHES + "](a[nrsŧ]|b[iuú]|e[cdglƚmnrstv-]|i[eégklƚmnoóstv]|[kpsv]a|[lntuéíòóú]|o[klƚmnrsx]|s[eé]|à[nrs]|èc|[ñv][aàeèéiíoòóuú]|[lƚ]o)"
    );
  }

  @Override
  public List<String> tokenize(String text) {
    List<String> list = new ArrayList<>();
    text = RegExUtils.replaceAll(text, patternApostrophe, "$1" + UNICODE_MODIFIER_LETTER_APOSTROPHE + "$2");
    final StringTokenizer st = new StringTokenizer(text, getTokenizingCharacters(), true);
    while(st.hasMoreElements()){
      final String token = st.nextToken();
      list.add(token);
    }
    list = joinEMails(list);
    list = joinUrls(list);
    return list;
  }

}
