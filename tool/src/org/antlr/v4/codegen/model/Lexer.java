/*
 [The "BSD license"]
 Copyright (c) 2011 Terence Parr
 All rights reserved.

 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions
 are met:

 1. Redistributions of source code must retain the above copyright
    notice, this list of conditions and the following disclaimer.
 2. Redistributions in binary form must reproduce the above copyright
    notice, this list of conditions and the following disclaimer in the
    documentation and/or other materials provided with the distribution.
 3. The name of the author may not be used to endorse or promote products
    derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.antlr.v4.codegen.model;

import org.antlr.v4.codegen.OutputModelFactory;
import org.antlr.v4.tool.*;

import java.util.*;

public class Lexer extends OutputModelObject {
	public String name;
	public Map<String,Integer> tokens;
	public LexerFile file;
	public String[] tokenNames;
	public Set<String> ruleNames;
	public Collection<String> modes;

	@ModelElement public SerializedATN atn;
	@ModelElement public LinkedHashMap<Integer, Action> actions;
	@ModelElement public LinkedHashMap<Integer, Action> sempreds;

	public Lexer(OutputModelFactory factory, LexerFile file) {
		this.factory = factory;
		this.file = file; // who contains us?
		Grammar g = factory.getGrammar();
		name = g.getRecognizerName();
		tokens = new LinkedHashMap<String,Integer>();
		LexerGrammar lg = (LexerGrammar)g;
		atn = new SerializedATN(factory, lg.atn);
		modes = lg.modes.keySet();

		for (String t : g.tokenNameToTypeMap.keySet()) {
			Integer ttype = g.tokenNameToTypeMap.get(t);
			if ( ttype>0 ) tokens.put(t, ttype);
		}

		tokenNames = g.getTokenDisplayNames();
		ruleNames = g.rules.keySet();

		sempreds = new LinkedHashMap<Integer, Action>();
		for (PredAST p : g.sempreds.keySet()) {
			sempreds.put(g.sempreds.get(p), new Action(factory, p));
		}
		actions = new LinkedHashMap<Integer, Action>();
		for (ActionAST a : g.actions.keySet()) {
			actions.put(g.actions.get(a), new Action(factory, a));
		}
	}

}
