Querity
=======

Documentation site

## How to make changes to the documentation

### Set up the local environment

The website is build with [Jekyll](https://jekyllrb.com/).

You can set up your local environment by
following [this guide](https://docs.github.com/en/pages/setting-up-a-github-pages-site-with-jekyll/creating-a-github-pages-site-with-jekyll)
OR you could just use a Docker container to not mess with your local machine.

If you choose the Docker option, you could create Bash command aliases to make it simpler to follow the guides around;
to do this, add the following lines to `~/.bash_aliases`:

```
alias jekyll='docker run --rm --volume="$(pwd):/srv/jekyll" --volume="$(pwd)/vendor/bundle:/usr/local/bundle" -p 4000:4000 -it jekyll/jekyll:4.2.0 jekyll'
alias bundle='docker run --rm --volume="$(pwd):/srv/jekyll" --volume="$(pwd)/vendor/bundle:/usr/local/bundle" -it jekyll/jekyll:4.2.0 bundle'
```

and restart the Bash command line to read the new aliases.

Configure bundler to download the dependencies in a project subdirectory:

```
bundle config set --local path 'vendor/bundle'
```

Finally, run `bundle install` to download the dependencies.

### Testing locally

Run `jekyll serve` in the root of the project and then open http://localhost:4000 in your web browser.
