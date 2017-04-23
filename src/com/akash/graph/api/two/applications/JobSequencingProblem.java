package com.akash.graph.api.two.applications;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

// Java program to find the maximum profit job sequence
// from a given array of jobs with deadlines and profits

// seems better approach than 
// http://www.geeksforgeeks.org/job-sequencing-using-disjoint-set-union/

class Job implements Comparable<Job> {
	char id;
	int deadline, profit;

	public Job(char id, int deadline, int profit) {
		this.id = id;
		this.deadline = deadline;
		this.profit = profit;
	}

	@Override
	public int compareTo(Job job) {
		// descending order
		return this.profit < job.profit ? 1 : -1;
	}

	@Override
	public String toString() {
		return "" + id + "-" + deadline + "-" + profit;
	}

	public static void printJobScheduling(List<Job> jobs) {
		Collections.sort(jobs);
		int numOfJobsProcessed = 0;
		for (Job job : jobs) {
			if (job.deadline > numOfJobsProcessed) {
				numOfJobsProcessed++;
				System.out.println(job);
			}
		}
	}
}

public class JobSequencingProblem {
	public static void main(String args[]) {
		List<Job> jobs = new ArrayList<Job>();
		jobs.add(new Job('a', 2, 100));
		jobs.add(new Job('b', 1, 19));
		jobs.add(new Job('c', 2, 27));
		jobs.add(new Job('d', 1, 25));
		jobs.add(new Job('e', 3, 15));
		System.out.println("Following jobs need to be " + "executed for maximum profit");
		Job.printJobScheduling(jobs);
	}
}