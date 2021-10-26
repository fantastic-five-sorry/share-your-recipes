# share-your-recipes

# implements spring security

1. Implements loadUserFromUsername() => get User from db
2. Implements UserDetails
3.

https://medium.com/@mail2rajeevshukla/hiding-encrypting-database-password-in-the-application-properties-34d59fe104eb

### run elastic search dev mode

```

docker run -d --name elasticsearch  -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:6.5.0

```
----

```
SELECT down_vote_count, up_vote_count, recipe_id FROM public.recipe_voting V inner join public.recipes R on V.recipe_id = R.id
-----

```
select  k1.*, k2.type from 

(SELECT c.*, u.name, u.photo_url
from comments c
inner join users u
on c.creator_id = u.id
where c.recipe_id=1000) as k1
left join 
(select v.voter_id, v.type, 
 v.comment_id
from comment_voting v 
where v.voter_id=1000) as k2
on k1.id=k2.comment_id
order by k1.id desc
```



