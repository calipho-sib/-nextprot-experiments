import json

with open('data.json', 'w') as outfile:
    statements = []    
    i = 0
    while(i < 1000000):
        statements.append({"IS_NEGATIVE": "true", "EXPRESSION_LEVEL": "not detected", "REFERENCE_DATABASE": "Bgee", "SOURCE": "Bgee", "ASSIGNED_BY": "Bgee", "STAGE_NAME": "80 year-old and over human stage (human)", "GENE_NAME": "COX4I1P2", "ANNOT_CV_TERM_TERMINOLOGY": "NextProt tissues", "ASSIGMENT_METHOD": "CURATED", "EVIDENCE_CODE": "ECO:0000295", "REFERENCE_ACCESSION": "ENSG00000228869", "RESOURCE_TYPE": "database", "SCORE": "17.37", "ANNOT_CV_TERM_NAME": "Brodmann (1909) area 9", "ANNOTATION_CATEGORY": "expression-profile", "EVIDENCE_QUALITY": "SILVER", "ENSEMBL_ID": "ENSG00000228869", "STAGE_ID": "HsapDO:0000095", "ANNOT_CV_TERM_ACCESSION": "TS-2658"})
        i = i + 1;
    json.dump(statements, outfile)
