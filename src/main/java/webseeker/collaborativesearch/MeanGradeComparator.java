package webseeker.collaborativesearch;

import java.util.Comparator;

import webseeker.faroo.LinkAndGrade;


public class MeanGradeComparator implements Comparator<LinkAndGrade> {

	public int compare(LinkAndGrade o1, LinkAndGrade o2) {
		if(o1.getMeanGrade()>o2.getMeanGrade()) return -1;
		else if(o1.getMeanGrade()<o2.getMeanGrade()) return 1;
		else return 0;
	}

}
