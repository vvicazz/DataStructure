package com.akash.graph.api.two.applications;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
//Java program to find the maximum profit job sequence
//from a given array of jobs with deadlines and profits

class DisjointSet {

	// if parent[i]==i , then i no of jobs can be adjusted <br>
	// else find(parent[i]) are the no of jobs that can be adjusted <br>
	int parent[];

	DisjointSet(int n) {
		parent = new int[n + 1];
		for (int i = 0; i <= n; i++)
			parent[i] = i;
	}

	int find(int s) {
		if (s == parent[s])
			return s;
		return parent[s] = find(parent[s]);
	}

	void merge(int u, int v) {
		// update the greatest available free slot to u
		parent[v] = u;
	}

	@Override
	public String toString() {
		return Arrays.toString(parent);
	}
}

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

	public static int findMaxDeadline(List<Job> arr) {
		int ans = Integer.MIN_VALUE;
		for (Job temp : arr)
			ans = Math.max(temp.deadline, ans);
		return ans;
	}

	public static void printJobScheduling(List<Job> jobs) {
		Collections.sort(jobs);
		int maxDeadline = findMaxDeadline(jobs);
		DisjointSet dsu = new DisjointSet(maxDeadline);
		System.out.println(dsu);
		for (Job job : jobs) {
			int availableSlot = dsu.find(job.deadline);
			if (availableSlot > 0) {
				dsu.merge(dsu.find(availableSlot - 1), availableSlot);
				System.out.println(job);
			}
			System.out.println(dsu);
		}
	}
}

public class JobSequencingProblem {
	public static void main(String args[]) {
		List<Job> jobs = new ArrayList<Job>();
		jobs.add(new Job('a', 1, 10));
		jobs.add(new Job('b', 2, 20));
		jobs.add(new Job('c', 3, 30));
		System.out.println("Following jobs need to be " + "executed for maximum profit");
		Job.printJobScheduling(jobs);
	}
}