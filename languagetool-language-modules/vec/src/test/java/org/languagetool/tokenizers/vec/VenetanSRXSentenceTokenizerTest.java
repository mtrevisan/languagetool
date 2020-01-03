/* LanguageTool, a natural language style checker
 * Copyright (C) 2014 Daniel Naber (http://www.danielnaber.de)
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

import org.junit.Test;
import org.languagetool.TestTools;
import org.languagetool.language.Venetan;
import org.languagetool.tokenizers.SRXSentenceTokenizer;

public class VenetanSRXSentenceTokenizerTest{

  private final SRXSentenceTokenizer stokenizer = new SRXSentenceTokenizer(new Venetan());

  //NOTE: sentences here need to end with a space character so they
  //have correct whitespace when appended:
  @Test
  public void testTokenize() {
    testSplit("Sta kuà");
    testSplit("Sta kuà la xe na fraxe ke la fenise ben. ", "Sta kuà no la xe na fraxe ke la fenise ben");

    testSplit("Sta kuà la xe na fraxe.");
    testSplit("Sta kuà la xe na fraxe. ", "E anka kuesta.");
    testSplit("Sta kuà la xe na fraxe.", "Xela vera?", "Sí, la xe.");
    testSplit("Sta kuà el xe, par ex., el Dot. Karlo Vièna, ke ʼl parla pian...",
      "Ma sta kuà la xe nʼ altra fraxe.");
    testSplit("La pòrta núm. 5 la xe saràa.");
    testSplit("El Señ. Mario el ga da dàr a Pièro 4.50 par kronpar el profumo Chanel No 5.",
      "No ʼl se gà mai prexentà.");
    testSplit("A pàx. 6 no ge xe ñente. ", "Nʼ altra fraxe.");
    testSplit("Ƚàsame in paxe!, el ge gà ŧigà drio. ", "Nʼ altra fraxe.");
    testSplit("\"Ƚàsame in paxe!\", el ge gà ŧigà drio. ");
    testSplit("'Ƚàsame in paxe!', el ge gà ŧigà drio. ", "Nʼ altra fraxe.");
    testSplit("No stà divíder... ", "Bèh, te sà. ", "Èko altre paròle.");
    testSplit("No stà divíder... bèh, te sà. ", "Èko altre paròle.");
    testSplit("No stà divíder!... ", "Bèh, te sà. ", "Èko altre paròle.");
    testSplit("El \".\" no ʼl gà da èser un delimitador.");
    testSplit("\"ʼArelo kuà!\" la ga dito.");
    testSplit("\"ʼArelo kuà!\", la ga dito.");
    testSplit("\"ʼArelo kuà.\" ", "Ma kuesta la xe nʼ altra fraxe.");
    testSplit("\"ʼArelo kuà!\". ", "Èko kòsa ke la gà dito.");
    testSplit("La fraxe la fenise kuà. ", "(Nʼ altra fraxe.)");
    testSplit("La fraxe (...) la fenise kuà.");
    testSplit("La fraxe [...] la fenise kuà.");
    testSplit("La fraxe la fenise kuà (...). ", "Nʼ altra fraxe.");
    testSplit("Na lista: un, do, trè.");
    testSplit("1) Primo ponto. ", "2) Sekondo ponto. ");
    testSplit("Stomegar (se varde Vienna, Carlo, 1855, pàx. 123). ");
    // Missing space after sentence end:
    testSplit("Joani el vièn da Belun!", "El vive a Trevixo dèso.");
    // parentheses:
    testSplit("El fonsiona (par davero!) tuto.");
    testSplit("El fonsiona [par davero!] tuto.");
    testSplit("El fonsiona (par davero!). ", "Fídate.");
    testSplit("El fonsiona [par davero!]. ", "Fida-te.");
    testSplit("El fonsiona(!) davero ben.");
    testSplit("El fonsiona[!] davero ben.");
    testSplit("El fonsiona (!) davero ben, staòlta.");
    testSplit("El fonsiona [!] davero ben, staòlta.");
    //deal with at least some nbsp that appear in strange places (e.g. Google Docs, web editors)
    testSplit("Proa.\u00A0\n", "Nʼ altra proa.");
    //not clear whether this is the best behavior...
    testSplit("Proa.\u00A0Nʼ altra proa.");

    //footnotes in LibOO/OOo look like this
    testSplit("Sta kuà la xe na fraxe.\u0002 ", "E sta kuà la xe nʼ altra.");
  }

  private void testSplit(String... sentences) {
    TestTools.testSplit(sentences, stokenizer);
  }

}
