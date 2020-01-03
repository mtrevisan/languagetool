/* LanguageTool, a natural language style checker
 * Copyright (C) 2007 Daniel Naber (http://www.danielnaber.de)
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
package org.languagetool.language;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import org.languagetool.Language;
import org.languagetool.LanguageMaintainedState;
import org.languagetool.UserConfig;
import org.languagetool.languagemodel.LanguageModel;
import org.languagetool.rules.*;
import org.languagetool.rules.vec.VenetanConfusionProbabilityRule;
import org.languagetool.rules.vec.VenetanWordRepeatRule;
import org.languagetool.rules.vec.MorfologikVenetanSpellerRule;
import org.languagetool.tokenizers.WordTokenizer;
import org.languagetool.tokenizers.vec.VenetanWordTokenizer;

import org.languagetool.tagging.Tagger;
import org.languagetool.tagging.vec.VenetanTagger;
import org.languagetool.tokenizers.SRXSentenceTokenizer;
import org.languagetool.tokenizers.SentenceTokenizer;

import org.languagetool.tagging.disambiguation.Disambiguator;
import org.languagetool.tagging.disambiguation.rules.vec.VenetanRuleDisambiguator;

/**
 * @author Mauro Trevisan
 */
public class Venetan extends Language implements AutoCloseable {

  private Tagger tagger;
  private SentenceTokenizer sentenceTokenizer;
  private LanguageModel languageModel;
  private WordTokenizer wordTokenizer;
  private Disambiguator disambiguator;

  @Override
  public String[] getCountries() {
    return new String[]{"IT"};
  }

  @Override
  public String getName() {
    return "Venetan";
  }

  @Override
  public String getShortCode() {
    return "vec";
  }

  @Override
  public Tagger getTagger() {
    if (tagger == null) {
      tagger = new VenetanTagger();
    }
    return tagger;
  }

  @Override
  public SentenceTokenizer getSentenceTokenizer() {
    if (sentenceTokenizer == null) {
      sentenceTokenizer = new SRXSentenceTokenizer(this);
    }
    return sentenceTokenizer;
  }

  @Override
  public Contributor[] getMaintainers() {
    Contributor contributor = new Contributor("Mauro Trevisan");
    return new Contributor[] { contributor };
  }

  @Override
  public List<Rule> getRelevantRules(ResourceBundle messages, UserConfig userConfig, Language motherTongue,
                                     List<Language> altLanguages) throws IOException {
    return Arrays.asList(
            new WhitespaceBeforePunctuationRule(messages),
            new CommaWhitespaceRule(messages),
            new DoublePunctuationRule(messages),
            new UppercaseSentenceStartRule(messages, this),
            new MultipleWhitespaceRule(messages, this),
            new SentenceWhitespaceRule(messages),
            new GenericUnpairedBracketsRule(messages,
                    Arrays.asList("[", "(", "{", "»", "«" /*"‘"*/),
                    Arrays.asList("]", ")", "}", "«", "»" /*"’"*/)),
            new MorfologikVenetanSpellerRule(messages, this, userConfig, altLanguages),
            new VenetanWordRepeatRule(messages, this)
    );
  }

  /** @since 3.1 */
  @Override
  public synchronized LanguageModel getLanguageModel(File indexDir) throws IOException {
    languageModel = initLanguageModel(indexDir, languageModel);
    return languageModel;
  }

  /** @since 3.1 */
  @Override
  public List<Rule> getRelevantLanguageModelRules(ResourceBundle messages, LanguageModel languageModel) throws IOException {
    return Arrays.asList(
            new VenetanConfusionProbabilityRule(messages, languageModel, this)
    );
  }

  @Override
  public WordTokenizer getWordTokenizer() {
    if (wordTokenizer == null) {
      wordTokenizer = new VenetanWordTokenizer();
    }
    return wordTokenizer;
  }

  /**
   * Closes the language model, if any.
   * @since 3.1
   */
  @Override
  public void close() throws Exception {
    if (languageModel != null) {
      languageModel.close();
    }
  }

  @Override
  public final Disambiguator getDisambiguator() {
    if (disambiguator == null) {
      disambiguator = new VenetanRuleDisambiguator();
    }
    return disambiguator;
  }

  @Override
  public LanguageMaintainedState getMaintainedState() {
    return LanguageMaintainedState.ActivelyMaintained;
  }

}
