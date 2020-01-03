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
import org.languagetool.tokenizers.WordTokenizer;
import org.languagetool.tokenizers.vec.VenetanWordTokenizer;

import java.io.IOException;

public class VenetanTaggerTest{

  private VenetanTagger tagger;
  private VenetanWordTokenizer tokenizer;

  @Before
  public void setUp() {
    tagger = new VenetanTagger();
    tokenizer = new VenetanWordTokenizer();
  }

  @Test
  public void testDictionary() throws IOException {
    TestTools.testDictionary(tagger, new Venetan());
  }

  @Test
  public void testTagger() throws IOException {
    TestTools.myAssert("So' drio 'ndar da mé nòna.",
      "Soʼ/[sora]AD\r|Soʼ/[sora]PR\r|Soʼ/[èser]VB\r -- drio/[drio]AD\r -- ʼndar/[ʼndar]VB\r -- da/[da]PR\r -- mé/[mé]JJP\r -- nòna/[nòna]NNs+f\r|nòna/[nòna]NOs+f\r", tokenizer, tagger);
    TestTools.myAssert("Soʼ drio ʼndar da mé nòna.",
        "Soʼ/[sora]AD\r|Soʼ/[sora]PR\r|Soʼ/[èser]VB\r -- drio/[drio]AD\r -- ʼndar/[ʼndar]VB\r -- da/[da]PR\r -- mé/[mé]JJP\r -- nòna/[nòna]NNs+f\r|nòna/[nòna]NOs+f\r", tokenizer, tagger);
    TestTools.myAssert("tuk-tuk", "tuk-tuk/[tuk-tuk]IN\r", tokenizer, tagger);
    TestTools.myAssert("blablabla", "blablabla/[null]null", tokenizer, tagger);
  }

}
