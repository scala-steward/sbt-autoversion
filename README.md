[![Release](https://github.com/sbt/sbt-autoversion/actions/workflows/release.yml/badge.svg)](https://github.com/sbt/sbt-autoversion/actions/workflows/release.yml)
[![CI](https://github.com/sbt/sbt-autoversion/actions/workflows/ci.yml/badge.svg)](https://github.com/sbt/sbt-autoversion/actions/workflows/ci.yml)
[![Latest version](https://img.shields.io/github/tag/sbt/sbt-autoversion.svg)](https://github.com/sbt/sbt-autoversion/releases)

# sbt-autoversion

The `sbt-autoversion` plugin builds on the [sbt-release](https://github.com/sbt/sbt-release) and [sbt-git](https://github.com/sbt/sbt-git) plugins to automatically manage the version bump to apply (major, minor or patch version bumps), based on commits messages patterns.

## Adding to your project

Add the following line to your `project/plugins.sbt`:

```scala
addSbtPlugin("com.github.sbt" % "sbt-autoversion" % "2.2.0")
```

Since `sbt-autoversion` is an AutoPlugin, it will be automatically available to your projects,
given you're including both the [sbt-release](https://github.com/sbt/sbt-release) and [sbt-git](https://github.com/sbt/sbt-git) plugins.

## Usage

`sbt-autoversion` automatically wires itself in the setting of sbt-release's `releaseVersion` setting, meaning that you can use the sbt-release's `release with-defaults` command and use the non-interactive release process with the correct version configured.

`sbt-autoversion` however expose a few interesting tasks:

* `latestTag`: fetches the latest Git tag, based on Semantic Versioning ordering
* `unreleasedCommits`: lists commits since the latest tag/release
* `suggestedBump`: shows what version bump the plugin has computed and would automatically apply on the next release.

## Settings

#### `tagNameCleaner`

Linked to sbt-release's `releaseTagName` setting, defines how to "clean up" a Git tag to get back a semver-compatible version.

#### `majorRegexes`, `minorRegexes`, `bugfixRegexes`

The list of regular expression that a commit message should match to be seen as requiring respectively a major, a minor, or a bugfix version bump.

Default patterns:

* major: `\[?breaking\]?.*`, `\[?major\]?.*`
* minor: `\[?feature\]?.*`, `\[?minor\]?.*`
* bugfix: `\[?bugfix\]?.*`, `\[?fix\]?.*`
* nano: `\[?nano\]?.*`

Note: regular expressions are executed in the order shown above (major, minor, then bugfix) and the first match is returned.
See `defaultBump` for behavior if no matches are found in the unreleased commit messages.

#### `defaultBump`

If the plugin is unable to suggest a version bump based on commit messages (i.e., if none of the configured regular expressions match), this version bump will be suggested instead.
If set to `None`, an error will be thrown, and the release will be aborted.

Set to `Some(Bump.Bugfix)` by default.

## Conventional Commits

An additional opt-in plugin is also provided that overrides the default bump patterns with those modelled after the [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/) spec:

* major: `.*BREAKING[-\s]CHANGE: .*`, `^(.*!: ).*`
* minor: `^feat.*: .*`
* bugfix: `^fix.*: .*`

The plugin must be manually enabled to take effect in `built.sbt`:

```
enablePlugins(AutoVersionPlugin, ConventionalCommits)
```

_Note_:  By default these patterns are applied _in addition_ to the default patterns, so that commit messages will match either the default or conventional commit patterns.  This default behavior can be disabled with this setting key, enabling _only_ Conventional commit patterns:

```
conventionalPatternsAdditive := false
```

## Maintenance

### CI
This project uses the [sbt-ci-release](https://github.com/sbt/sbt-ci-release) for generating GitHub actions in the `.github` actions directory, based on declared build configuration in `github-actions.sbt`. 

When making changes to `github-actions.sbt` job declarations, the Github actions should be updated to reflect these changes by running the following task:

```
sbt githubWorkflowGenerate
```

### Releasing

To release a new version of this plugin simply create a new tag against the intended release commit which should trigger the `release` Gitbhub action. 


## License

This software is under the [Apache 2.0 License](http://www.apache.org/licenses/LICENSE-2.0.html).
