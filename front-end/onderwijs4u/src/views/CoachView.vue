<template>
  <div class="container mt-5">
    <h1 class="mb-4">Student Progress Overview</h1>

    <div class="table-responsive">
      <table class="table table-hover">
        <thead>
          <tr>
            <th @click="sort('name')" class="sortable">
              Name
              <SortIcon :active="sortColumn === 'name'" :ascending="sortOrder === 'asc'" />
            </th>
            <th @click="sort('progress')" class="sortable">
              Progress
              <SortIcon :active="sortColumn === 'progress'" :ascending="sortOrder === 'asc'" />
            </th>
            <th @click="sort('yearOfStudy')" class="sortable">
              Year of Study
              <SortIcon :active="sortColumn === 'yearOfStudy'" :ascending="sortOrder === 'asc'" />
            </th>
            <th @click="sort('needForHelp')" class="sortable">
              Support score
              <SortIcon :active="sortColumn === 'needForHelp'" :ascending="sortOrder === 'asc'" />
            </th>
          </tr>
        </thead>
        <tbody>
          <tr v-for="student in paginatedStudents" :key="student.id" :class="getRowClass(student.needForHelp)">
            <td class="name-cell">
              <RouterLink class="nav-link" :to="`/rubrics`">
                {{ student.name }}
              </RouterLink>
            </td>
            <td>
              <div class="progress" style="height: 20px;">
                <div
                  class="progress-bar"
                  role="progressbar"
                  :style="{ width: `${student.progress}%` }"
                  :aria-valuenow="student.progress"
                  aria-valuemin="0"
                  aria-valuemax="100"
                >{{ student.progress }}%</div>
              </div>
            </td>
            <td>{{ student.yearOfStudy }}</td>
            <td>
              <span
                class="badge"
                :class="getNeedForHelpClass(student.needForHelp)"
              >
                {{ student.needForHelp.toFixed(2) }}
              </span>
            </td>
          </tr>
        </tbody>
      </table>
    </div>

    <nav aria-label="Student table navigation">
      <ul class="pagination justify-content-center">
        <li class="page-item" :class="{ disabled: currentPage === 1 }">
          <a class="page-link" href="#" @click.prevent="changePage(currentPage - 1)">Previous</a>
        </li>
        <li v-for="page in totalPages" :key="page" class="page-item" :class="{ active: currentPage === page }">
          <a class="page-link" href="#" @click.prevent="changePage(page)">{{ page }}</a>
        </li>
        <li class="page-item" :class="{ disabled: currentPage === totalPages }">
          <a class="page-link" href="#" @click.prevent="changePage(currentPage + 1)">Next</a>
        </li>
      </ul>
    </nav>
  </div>
</template>

<script setup>
import {ref, computed, h} from 'vue'
import { ChevronUp, ChevronDown } from 'lucide-vue-next'

const SortIcon = {
  props: {
    active: Boolean,
    ascending: Boolean
  },
  setup(props) {
    return () => h('span', { class: `sort-icon ${props.active ? 'active' : ''}` }, [
      props.active && props.ascending
        ? h(ChevronUp, { size: 14 })
        : h(ChevronDown, { size: 14 })
    ])
  }
}

const students = ref([
  { id: 1, name: 'Alice Johnson', progress: 75, yearOfStudy: 3 },
  { id: 2, name: 'Bob Smith', progress: 45, yearOfStudy: 2 },
  { id: 3, name: 'Charlie Brown', progress: 90, yearOfStudy: 4 },
  { id: 4, name: 'Diana Prince', progress: 30, yearOfStudy: 4 },
  { id: 5, name: 'Ethan Hunt', progress: 60, yearOfStudy: 3 },
  { id: 6, name: 'Fiona Gallagher', progress: 15, yearOfStudy: 1 },
  { id: 7, name: 'George Weasley', progress: 50, yearOfStudy: 2 },
  { id: 8, name: 'Hermione Granger', progress: 95, yearOfStudy: 3 },
  { id: 9, name: 'Ian Malcolm', progress: 40, yearOfStudy: 4 },
  { id: 10, name: 'Julia Child', progress: 70, yearOfStudy: 3 },
  { id: 11, name: 'Kevin Hart', progress: 55, yearOfStudy: 2 },
  { id: 12, name: 'Lila Rodriguez', progress: 85, yearOfStudy: 4 },
  { id: 13, name: 'Mike Tyson', progress: 20, yearOfStudy: 1 },
  { id: 14, name: 'Nancy Drew', progress: 65, yearOfStudy: 3 },
  { id: 15, name: 'Oscar Wilde', progress: 60, yearOfStudy: 4 },
  { id: 16, name: 'Penny Lane', progress: 35, yearOfStudy: 2 },
  { id: 17, name: 'Quentin Tarantino', progress: 70, yearOfStudy: 3 },
  { id: 18, name: 'Rachel Green', progress: 50, yearOfStudy: 2 },
  { id: 19, name: 'Steve Jobs', progress: 95, yearOfStudy: 4 },
  { id: 20, name: 'Tina Fey', progress: 60, yearOfStudy: 3 },
  { id: 21, name: 'Uma Thurman', progress: 40, yearOfStudy: 2 },
  { id: 22, name: 'Vincent Vega', progress: 75, yearOfStudy: 3 },
  { id: 23, name: 'Walter White', progress: 85, yearOfStudy: 4 },
  { id: 24, name: 'Xena Warrior', progress: 30, yearOfStudy: 1 },
  { id: 25, name: 'Yoda Master', progress: 100, yearOfStudy: 4 },
  { id: 26, name: 'Zack Morris', progress: 55, yearOfStudy: 2 },
  { id: 27, name: 'Amy Winehouse', progress: 70, yearOfStudy: 3 },
  { id: 28, name: 'Bruce Wayne', progress: 90, yearOfStudy: 4 },
  { id: 29, name: 'Carrie Bradshaw', progress: 65, yearOfStudy: 3 },
  { id: 30, name: 'Darth Vader', progress: 80, yearOfStudy: 4 },
  { id: 31, name: 'Emma Watson', progress: 95, yearOfStudy: 4 },
  { id: 32, name: 'Frodo Baggins', progress: 50, yearOfStudy: 2 },
  { id: 33, name: 'Gollum Smeagol', progress: 25, yearOfStudy: 1 },
  { id: 34, name: 'Harry Potter', progress: 85, yearOfStudy: 4 },
  { id: 35, name: 'Indiana Jones', progress: 70, yearOfStudy: 3 },
  { id: 36, name: 'Jack Sparrow', progress: 60, yearOfStudy: 2 },
  { id: 37, name: 'Katniss Everdeen', progress: 80, yearOfStudy: 3 },
  { id: 38, name: 'Luke Skywalker', progress: 90, yearOfStudy: 4 },
  { id: 39, name: 'Marty McFly', progress: 75, yearOfStudy: 3 },
  { id: 40, name: 'Neo Anderson', progress: 95, yearOfStudy: 4 },
])

const sortColumn = ref('needForHelp')
const sortOrder = ref('desc')
const currentPage = ref(1)
const itemsPerPage = 10

const calculateNeedForHelp = (student) => {
  const expectedProgress = student.yearOfStudy * 25
  return expectedProgress - student.progress
}

// Add needForHelp property to each student
students.value.forEach(student => {
  student.needForHelp = calculateNeedForHelp(student)
})

const sortedStudents = computed(() => {
  return [...students.value].sort((a, b) => {
    let comparison = 0
    if (a[sortColumn.value] < b[sortColumn.value]) comparison = -1
    if (a[sortColumn.value] > b[sortColumn.value]) comparison = 1
    return sortOrder.value === 'asc' ? comparison : -comparison
  })
})

const paginatedStudents = computed(() => {
  const start = (currentPage.value - 1) * itemsPerPage
  const end = start + itemsPerPage
  return sortedStudents.value.slice(start, end)
})

const totalPages = computed(() => Math.ceil(students.value.length / itemsPerPage))

const sort = (column) => {
  if (sortColumn.value === column) {
    sortOrder.value = sortOrder.value === 'asc' ? 'desc' : 'asc'
  } else {
    sortColumn.value = column
    sortOrder.value = 'asc'
  }
}

const changePage = (page) => {
  if (page >= 1 && page <= totalPages.value) {
    currentPage.value = page
  }
}

const getNeedForHelpClass = (needForHelp) => {
  if (needForHelp > 50) return 'bg-danger'
  if (needForHelp > 25) return 'bg-warning'
  return 'bg-success'
}

const getRowClass = (needForHelp) => {
  if (needForHelp > 50) return 'table-danger'
  if (needForHelp > 25) return 'table-warning'
  return 'table-success'
}
</script>

<style scoped>
.progress {
  height: 20px;
}
.progress-bar {
  line-height: 20px;
}
.table-responsive {
  margin-bottom: 1rem;
}
.sortable {
  cursor: pointer;
  user-select: none;
}
.sort-icon {
  display: inline-block;
  width: 14px;
  height: 14px;
  margin-left: 5px;
  vertical-align: middle;
}
.sort-icon.active {
  color: #007bff;
}
.name-cell {
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  max-width: 200px;
}
.table th, .table td {
  vertical-align: middle;
}
.badge {
  font-size: 0.9em;
  padding: 0.4em 0.6em;
}
</style>