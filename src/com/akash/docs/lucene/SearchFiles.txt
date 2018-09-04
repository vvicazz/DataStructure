package org.akash.lucene;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;

//lucene.version = 7.4.0
public class SearchFiles {

	public static void main(String[] args) throws Exception {

		String index = "C:\\Users\\akash.sharma\\Desktop\\lucene\\index";
		String field = "contents";
		String queryString = "contents:thread";
		int hitsPerPage = 10;

		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(index)));
		IndexSearcher searcher = new IndexSearcher(reader);
		Analyzer analyzer = new StandardAnalyzer();

		QueryParser parser = new QueryParser(field, analyzer);
		Query query = parser.parse(queryString);

		doPagingSearch(searcher, query, hitsPerPage);
		reader.close();
	}

	public static void doPagingSearch(IndexSearcher searcher, Query query, int hitsPerPage) throws IOException {
		TopDocs results = searcher.search(query, 5 * hitsPerPage);
		ScoreDoc[] hits = results.scoreDocs;
		int numTotalHits = Math.toIntExact(results.totalHits);
		System.out.println(numTotalHits + " total matching documents");
		for (ScoreDoc scoreDoc : hits) {
			System.out.println("doc id : " + scoreDoc.doc + " , score : " + scoreDoc.score);
			System.out.println(searcher.doc(scoreDoc.doc));
		}
	}
}
