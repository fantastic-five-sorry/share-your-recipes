$(document).ready(function () {
  console.log('ready');
  $('#searchForm').submit((e) => {
    e.preventDefault();
    const query = $('#search-text').val();
    window.location.href = '/search?q=' + query;
  });
});
