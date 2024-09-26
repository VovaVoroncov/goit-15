document.addEventListener('DOMContentLoaded', function() {
  const themeToggleBtn = document.getElementById('themeToggleBtn');
  const lightThemeIcon = document.getElementById('lightThemeIcon');
  const darkThemeIcon = document.getElementById('darkThemeIcon');
  const body = document.body;

  // Check theme in localStorage
  const savedTheme = localStorage.getItem('theme');
  if (savedTheme) {
    body.classList.add(savedTheme);
    applyTheme(savedTheme);
  } else {
    body.classList.add('light-theme');
  }

  themeToggleBtn.addEventListener('click', function () {
    body.classList.toggle('dark-theme');
    body.classList.toggle('light-theme');

    // Save in storage
    const currentTheme = body.classList.contains('dark-theme') ? 'dark-theme' : 'light-theme';
    localStorage.setItem('theme', currentTheme);

    applyTheme(currentTheme);
    updateThemeIcon(currentTheme);
  });

  // Apply theme
  function applyTheme(theme) {
    if (theme === 'dark-theme') {
      document.documentElement.style.setProperty('--background-color', '#001410');
      document.documentElement.style.setProperty('--text-color', '#F7F7F7');
      document.documentElement.style.setProperty('--card-background-color', '#005C49');
    } else {
      document.documentElement.style.setProperty('--background-color', '#E9FFE8');
      document.documentElement.style.setProperty('--text-color', '#000000');
      document.documentElement.style.setProperty('--card-background-color', '#FAFFB0');
    }
  }

  // Update theme icon
  function updateThemeIcon(theme) {
    if (theme === 'light-theme') {
      lightThemeIcon.classList.remove('d-none');
      darkThemeIcon.classList.add('d-none');
    } else {
      lightThemeIcon.classList.add('d-none');
      darkThemeIcon.classList.remove('d-none');
    }
  }

  // Update theme icon on page load
  const currentTheme = body.classList.contains('dark-theme') ? 'dark-theme' : 'light-theme';
  updateThemeIcon(currentTheme);
});

function autoResize() {
  const textarea = document.getElementById("content");
  textarea.style.height = "auto";
  textarea.style.height = `${textarea.scrollHeight}px`;
}
