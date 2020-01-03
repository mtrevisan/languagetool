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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.languagetool.tokenizers.WordTokenizer;

/**
 * @author Mauro Trevisan
 */
public class VenetanWordTokenizer extends WordTokenizer {

  private static final String UNICODE_APOSTROPHE = "'";
  private static final String UNICODE_MODIFIER_LETTER_APOSTROPHE = "\u02BC";
  private static final String UNICODE_APOSTROPHES_PATTERN = "[" + UNICODE_APOSTROPHE + UNICODE_MODIFIER_LETTER_APOSTROPHE + "]";

  private static final Pattern APOSTROPHE = Pattern.compile("(?i)"
    + "([dglƚnsv]|(a|[ai\u2019]n)dó|[kps]o|pu?ò|st|tan|kuan|tut|([n\u2019]|in)t|tèr[sŧ]|k[uo]art|kuint|sèst|[kp]a|sen[sŧ]|komò|fra|nu|re|intor)" + UNICODE_APOSTROPHES_PATTERN + "(?=[" + Pattern.quote(getTokenizingCharacters()) + "])"
    + "|"
    + "(?<=\\s)" + UNICODE_APOSTROPHES_PATTERN + "[^" + Pattern.quote(getTokenizingCharacters()) + "]+"
  );


  @Override
  public List<String> tokenize(final String text) {
    List<String> list = new ArrayList<>();
    final StringTokenizer st = new StringTokenizer(text, getTokenizingCharacters(), true);
    while(st.hasMoreElements()){
      final String token = st.nextToken();
      list.add(token);
    }
    list = joinApostrophes(list);
    list = joinEMails(list);
    list = joinUrls(list);
    return list;
  }

  protected List<String> joinApostrophes(final List<String> list){
    final StringBuilder sb = new StringBuilder();
    for(final String item : list){
      sb.append(item);
    }
    final String text = sb.toString();
    if(text.contains(UNICODE_APOSTROPHE) || text.contains(UNICODE_MODIFIER_LETTER_APOSTROPHE)){
      final List<String> l = new ArrayList<>();

      final Matcher matcher = APOSTROPHE.matcher(text);
      int currentPosition = 0;
      int idx = 0;
      while(matcher.find()){
        final int start = matcher.start();
        final int end = matcher.end();
        while(currentPosition < end){
          if(currentPosition < start){
            l.add(list.get(idx));
          }
          else if(currentPosition == start){
            //substitute Unicode Apostrophe with Unicode Modifier Letter Apostrophe
            final String group = matcher.group()
              .replaceAll(UNICODE_APOSTROPHE, UNICODE_MODIFIER_LETTER_APOSTROPHE);
            l.add(group);
          }
          currentPosition += list.get(idx)
            .length();
          idx ++;
        }
      }
      if(currentPosition < text.length()){
        l.addAll(list.subList(idx, list.size()));
      }

      return l;
    }
    return list;
  }

}
