/* LanguageTool, a natural language style checker
 * Copyright (C) 2005 Daniel Naber (http://www.danielnaber.de)
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
package org.languagetool.tagging.vec.disambiguation.rules.vec;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.TestTools;
import org.languagetool.language.Venetan;
import org.languagetool.tagging.disambiguation.rules.XmlRuleDisambiguator;
import org.languagetool.tagging.vec.VenetanTagger;
import org.languagetool.tokenizers.SRXSentenceTokenizer;
import org.languagetool.tokenizers.SentenceTokenizer;
import org.languagetool.tokenizers.WordTokenizer;

import java.io.IOException;


public class VenetanDisambiguationRuleTest{

  private VenetanTagger tagger;
  private WordTokenizer tokenizer;
  private SentenceTokenizer sentenceTokenizer;
  private XmlRuleDisambiguator disambiguator;

  @Before
  public void setUp() {
    tagger = new VenetanTagger();
    tokenizer = new WordTokenizer();
    sentenceTokenizer = new SRXSentenceTokenizer(new Venetan());
    disambiguator = new XmlRuleDisambiguator(new Venetan());
  }

  @Test
  public void testPersonalArticleAndFemenineProperNoun() throws IOException {
    TestTools.myAssert("Gò visto la Lugrèŧia.",
      "/[null]SENT_START Gò/[gaver]VB|Gò/[gò]NN+s+m  /[null]null visto/[vistir]VB|visto/[véder]VB+s+m  /[null]null la/[la]AD+s+f  /[null]null Lugrèŧia/[Lugrèŧia]NNP+s+f ./[null]null",
      tokenizer, sentenceTokenizer, tagger, disambiguator);
    TestTools.myAssert("Gò visto la bèla Lugrèŧia.",
      "/[null]SENT_START Gò/[gaver]VB|Gò/[gò]NN+s+m  /[null]null visto/[vistir]VB|visto/[véder]VB+s+m  /[null]null la/[la]AD+s+f  /[null]null bèla/[bèla]IN+s+f|bèla/[bèla]JJ+s+f  /[null]null Lugrèŧia/[Lugrèŧia]NNP+s+f ./[null]null",
      tokenizer, sentenceTokenizer, tagger, disambiguator);
    TestTools.myAssert("Gò visto la molto bèla Lugrèŧia.",
      "/[null]SENT_START Gò/[gaver]VB|Gò/[gò]NN+s+m  /[null]null visto/[vistir]VB|visto/[véder]VB+s+m  /[null]null la/[la]AD+s+f  /[null]null molto/[molto]AD|molto/[molto]JJ+s+m|molto/[molto]QE+s+m  /[null]null bèla/[bèla]IN+s+f|bèla/[bèla]JJ+s+f  /[null]null Lugrèŧia/[Lugrèŧia]NNP+s+f ./[null]null",
      tokenizer, sentenceTokenizer, tagger, disambiguator);
//    TestTools.myAssert("Gò visto la granda e bèla Lugrèŧia.",
//      "/[null]SENT_START Gò/[gaver]VB|Gò/[gò]NN+s+m  /[null]null visto/[vistir]VB|visto/[véder]VB+s+m  /[null]null la/[la]AD+s+f  /[null]null granda/[granda]JJ+s+f|granda/[granda]NN+s+f  /[null]null molto/[molto]AD|molto/[molto]JJ+s+m  /[null]null bèla/[bèla]IN+s+f|bèla/[bèla]JJ+s+f  /[null]null Lugrèŧia/[Lugrèŧia]NNP+s+f ./[null]null",
//      tokenizer, sentenceTokenizer, tagger, disambiguator);
  }

}


