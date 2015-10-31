SELECT * FROM files;
SELECT * FROM words;
SELECT * FROM wordsfiles;

SELECT * FROM documents WHERE id IN (Select td.document_id FROM terms t, terms_documents td WHERE t.term = "trabaho" AND t.id = td.term_id)
AND id IN (Select td.document_id FROM terms t, terms_documents td WHERE t.term = "gastos" AND t.id = td.term_id); 