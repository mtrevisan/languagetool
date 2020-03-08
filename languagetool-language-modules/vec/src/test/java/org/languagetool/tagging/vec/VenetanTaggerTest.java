/* LanguageTool, a natural language style checker
 * Copyright (C) 2006 Daniel Naber (http://www.danielnaber.de)
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
package org.languagetool.tagging.vec;

import org.junit.Before;
import org.junit.Test;
import org.languagetool.TestTools;
import org.languagetool.language.Venetan;
import org.languagetool.tokenizers.vec.VenetanWordTokenizer;

import java.io.IOException;

public class VenetanTaggerTest{

  private VenetanTagger tagger;
  private VenetanWordTokenizer tokenizer;

  @Before
  public void setUp() {
    tagger = new VenetanTagger(new Venetan());
    tokenizer = new VenetanWordTokenizer();
  }

  @Test
  public void testDictionary() throws IOException {
    TestTools.testDictionary(tagger, new Venetan());
  }

  @Test
  public void testTagger() throws IOException {
    TestTools.myAssert("So' drio 'ndar da mé nòna.",
      "Soʼ/[sora]AD|Soʼ/[sora]PR|Soʼ/[èser]VB -- drio/[drio]AD -- ʼndar/[ʼndar]VB -- da/[da]PR -- mé/[mé]JPW -- nòna/[nòna]NN+s+f|nòna/[nòna]NO+s+f", tokenizer, tagger);
    TestTools.myAssert("Soʼ drio ʼndar da mé nòna.",
        "Soʼ/[sora]AD|Soʼ/[sora]PR|Soʼ/[èser]VB -- drio/[drio]AD -- ʼndar/[ʼndar]VB -- da/[da]PR -- mé/[mé]JPW -- nòna/[nòna]NN+s+f|nòna/[nòna]NO+s+f", tokenizer, tagger);
    TestTools.myAssert("tuk-tuk", "tuk-tuk/[tuk-tuk]IN", tokenizer, tagger);
    TestTools.myAssert("blablabla", "blablabla/[null]null", tokenizer, tagger);

//    TestTools.myAssert("Gò visto la granda e bèla Lugrèŧia.",
//      "Gò/[gaver]VB|Gò/[gò]NN+s+m -- visto/[vistir]VB|visto/[véder]VB+s+m -- la/[la]AD+s+f|la/[la]AP+s+f|la/[la]PKCD+3+s+f|la/[la]PKS+3+s+f -- granda/[granda]JJ+s+f|granda/[granda]NN+s+f -- e/[e]CN -- molto/[molto]AD|molto/[molto]JJ+s+m -- bèla/[bèla]IN+s+f|bèla/[bèla]JJ+s+f -- Lugrèŧia/[Lugrèŧia]NNP+s+f",
//      tokenizer, tagger);
  }

}
