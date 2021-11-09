package com.github.javlock.system.apiutils.repo.git;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PullResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.FetchResult;
import org.eclipse.jgit.transport.TrackingRefUpdate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.javlock.system.apidata.Paths;

public class GitHelper {
	private static final Logger LOGGER = LoggerFactory.getLogger("GitHelper");

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

	public static boolean updateRepo() throws GitAPIException, IOException {
		try (Git git = Git.open(Paths.repoDir)) {
			PullResult pullResult = git.pull().call();

			if (pullResult.isSuccessful()) {
				LOGGER.info("pullResult.isSuccessful()");
				FetchResult result = pullResult.getFetchResult();

				int index = 0;

				for (TrackingRefUpdate trackingRefUpdate : result.getTrackingRefUpdates()) {
					LOGGER.info("ID:{} msg:{}", index,
							ToStringBuilder.reflectionToString(trackingRefUpdate, ToStringStyle.MULTI_LINE_STYLE));
					index++;
				}
				return !result.getTrackingRefUpdates().isEmpty();
			}
		}

		return false;
	}

}
