package com.github.javlock.system.apiutils.repo.git;

import java.io.File;

import org.eclipse.jgit.api.Git;

public class GitHelper {

	public static boolean getRepo(String repoUrl, File repoDir, String branch) {
		try {
			if (repoDir.exists()) {
				try (Git git = Git.open(repoDir);) {
					git.pull().call();
				}
			} else {
				Git.cloneRepository().setURI(repoUrl).setDirectory(repoDir).setBranch(branch).setCloneAllBranches(true)
						.call();
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
